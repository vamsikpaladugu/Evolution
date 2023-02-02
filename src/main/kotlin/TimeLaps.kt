import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.math.pow

class TimeLaps {

    private var maleMap = mutableMapOf<Long, Species>()
    private var femaleMap = mutableMapOf<Long, Species>()

    private var populationLifeSpan = 0

    private var fCount = 0L
    private var mCount = 0L

    private var spCount = 1;


    /*
    * create 200 species with Random positions, gender and lifespan
    * */
    fun init() {

        for (i in 0..200) {

            val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 0 else 1

            val posX = Random.nextInt(0, SpaceX)
            val posY = Random.nextInt(0, SpaceX)

            val lifeTime = Random.nextInt(preAdultAge, maxLifeSpan)

            if (gender == 0) {
                maleMap[mCount] = Species(mCount++, posX, posY, lifeTime, gender, -1, 0)
            } else {
                femaleMap[fCount] = Species(fCount++, posX, posY, lifeTime, gender, -1, 0, isItSpecial = mCount==2L)
            }

        }

        snap()

    }

    private fun snap() {

        while (maleMap.isNotEmpty() && femaleMap.isNotEmpty()) {

            val mKeySet = maleMap.keys.toSet()
            val fKeySet = femaleMap.keys.toSet()


            //Create new species
            fKeySet.forEach {

                val sp = femaleMap[it]

                if (sp != null) {
                    if (sp.freezeTime != -1) {
                        if (sp.lifeTime - sp.freezeTime == 10) {

                            val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 0 else 1

                            if (gender == 0) {
                                maleMap[mCount] = Species(mCount++, sp.x, sp.y, 0, gender, -1, 0, isItSpecial = sp.canHaveSpecialChild)
                            } else {
                                femaleMap[fCount] = Species(fCount++, sp.x, sp.y, 0, gender, -1, 0, isItSpecial = sp.canHaveSpecialChild)
                            }

                            if (sp.canHaveSpecialChild) spCount++

                        } else if (sp.lifeTime - sp.freezeTime == 20) {
                            femaleMap[it]?.freezeTime = -1
                            femaleMap[it]?.canHaveSpecialChild = false

                        }
                    }
                }

            }

            mKeySet.forEach {
                if (maleMap[it]!!.lifeTime - maleMap[it]!!.freezeTime == 20) maleMap[it]!!.freezeTime = -1
            }


            //Increase lifetime
            for (i in mKeySet) {
                maleMap[i]!!.lifeTime++
            }

            for (i in fKeySet) {
                femaleMap[i]!!.lifeTime++
            }


            // Remove dead species
            mKeySet.forEach { i ->
                if (maleMap[i]!!.lifeTime >= maxLifeSpan || maleMap[i]!!.childCount == maxChildrenPerSpecies) maleMap.remove(
                    i
                )
            }

            fKeySet.forEach { i ->
                if (femaleMap[i]!!.lifeTime >= maxLifeSpan || femaleMap[i]!!.childCount == maxChildrenPerSpecies) femaleMap.remove(
                    i
                )
            }

            //createNewSpecies()
            moveSpecies()

            populationLifeSpan++

            //println("at = " + populationLifeSpan + " -> female = " + femaleMap.keys.size + ", male = " + maleMap.keys.size + " M = " + " , = " + (maleMap.keys.size.toFloat() / femaleMap.keys.size.toFloat()))
            println("{\"x\":$populationLifeSpan,\"y\":${(femaleMap.keys.size + maleMap.keys.size - spCount)},\"z\":$spCount},")

        }

    }

    private fun moveSpecies() {

        maleMap.keys.forEach {

            val mSpecies = maleMap[it]

            if (mSpecies!!.freezeTime == -1 && mSpecies!!.lifeTime >= preAdultAge) {

                var closestFemaleSpeciesKey = Long.MAX_VALUE
                var closestFemaleSpeciesDistance = Double.MAX_VALUE

                femaleMap.keys.forEach { fit ->

                    if (femaleMap[fit]!!.freezeTime == -1 && femaleMap[fit]!!.lifeTime >= preAdultAge && !femaleMap[fit]!!.isInLove) {

                        val distance = sqrt(
                            (femaleMap[fit]!!.x - mSpecies!!.x).toDouble().pow(2)
                                    + (femaleMap[fit]!!.y - mSpecies!!.y).toDouble().pow(2)
                        )

                        if (distance <= closestFemaleSpeciesDistance) {
                            closestFemaleSpeciesDistance = distance
                            closestFemaleSpeciesKey = fit
                        }

                    }

                }

                if (closestFemaleSpeciesKey != Long.MAX_VALUE && (
                            Random.nextInt(0, 100) % 4 == 0 ||
                                    femaleMap[closestFemaleSpeciesKey]!!.isItSpecial ||
                                    maleMap[it]!!.isItSpecial
                            )
                ) {

                    maleMap[it]!!.freezeTime = maleMap[it]!!.lifeTime
                    femaleMap[closestFemaleSpeciesKey]!!.freezeTime = femaleMap[closestFemaleSpeciesKey]!!.lifeTime
                    femaleMap[closestFemaleSpeciesKey]!!.canHaveSpecialChild =
                        femaleMap[closestFemaleSpeciesKey]!!.isItSpecial || maleMap[it]!!.isItSpecial

                    maleMap[it]!!.x = femaleMap[closestFemaleSpeciesKey]!!.x
                    maleMap[it]!!.y = femaleMap[closestFemaleSpeciesKey]!!.y

                } else {

                    when (Random.nextInt(0, 4)) {
                        0 -> {
                            maleMap[it]!!.x -= 1
                        }

                        1 -> {
                            maleMap[it]!!.x += 1
                        }

                        2 -> {
                            maleMap[it]!!.y -= 1
                        }

                        3 -> {
                            maleMap[it]!!.y += 1
                        }
                    }

                }

                if (closestFemaleSpeciesKey != Long.MAX_VALUE) {
                    femaleMap[closestFemaleSpeciesKey]!!.isInLove = true
                }
                maleMap[it]!!.isInLove = true

            }


            femaleMap.keys.forEach { it1 ->

                femaleMap[it1]!!.isInLove = false

                if (femaleMap[it1]!!.freezeTime == -1) {

                    when (Random.nextInt(0, 4)) {
                        0 -> {
                            femaleMap[it1]!!.x -= 1
                        }

                        1 -> {
                            femaleMap[it1]!!.x += 1
                        }

                        2 -> {
                            femaleMap[it1]!!.y -= 1
                        }

                        3 -> {
                            femaleMap[it1]!!.y += 1
                        }
                    }
                }

            }

            maleMap.keys.forEach { it1 ->
                maleMap[it1]!!.isInLove = false
            }

        }


    }

}