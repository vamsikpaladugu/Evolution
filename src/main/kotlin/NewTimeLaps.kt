import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class NewTimeLaps {

    private var maleMap = mutableMapOf<Long, Species>()
    private var femaleMap = mutableMapOf<Long, Species>()

    private var populationLifeSpan = 0

    private var fCount = 0L
    private var mCount = 0L

    private var balancer = 10
    private var interaptur = Random.nextInt(1,10)


    /*
    * create 200 species with Random positions, gender and lifespan
    * */
    fun init() {

        for (i in 0..200) {

            val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 0 else 1

            val posX = Random.nextInt(0, SpaceX)
            val posY = Random.nextInt(0, SpaceX)

            val lifeTime = Random.nextInt(200, 400)

            if (gender == 0) {
                maleMap[mCount] = Species(mCount++, posX, posY, lifeTime, gender, -1, 0)
            } else {
                femaleMap[fCount] = Species(fCount++, posX, posY, lifeTime, gender, -1, 0)
            }

        }

        snap()

    }

    private fun snap() {

        while (maleMap.isNotEmpty() && femaleMap.isNotEmpty()) {

            val mKeySet = maleMap.keys.toSet()
            val fKeySet = femaleMap.keys.toSet()


            //Create new species

            var newSpeciesM = 0
            var newSpeciesF = 0

            fKeySet.forEach {

                val sp = femaleMap[it]

                if (sp != null) {
                    if (sp.freezeTime != -1) {
                        if (sp.lifeTime - sp.freezeTime == 10) {

                            //val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 0 else 1
                            var gender = 0

                            if (balancer == 0) {
                                balancer = 10
                                interaptur = Random.nextInt(1,5)
                            }

                            if (balancer%2 == 0 && interaptur*2 != balancer) {
                                gender = 1
                            }
                            balancer--

                            //println("b = "+balancer +", Gender = "+gender)


                            if (gender == 0) {
                                newSpeciesM++
                                maleMap[mCount] = Species(mCount++, sp.x, sp.y, 0, gender, -1, 0)
                            } else {
                                newSpeciesF++
                                femaleMap[fCount] = Species(fCount++, sp.x, sp.y, 0, gender, -1, 0)
                            }

                        } else if (sp.lifeTime - sp.freezeTime == 20) {
                            femaleMap[it]?.freezeTime = -1
                        }
                    }
                }

            }

            mKeySet.forEach {
                if (maleMap[it]!!.lifeTime - maleMap[it]!!.freezeTime == 20) maleMap[it]!!.freezeTime = -1
            }


            //Increase life span
            for (i in mKeySet) {
                maleMap[i]!!.lifeTime++
            }

            for (i in fKeySet) {
                femaleMap[i]!!.lifeTime++
            }



            // Remove dead species
            for (i in mKeySet) {
                if (maleMap[i]!!.lifeTime >= 400 || maleMap[i]!!.childCount == 4) maleMap.remove(i)
            }

            for (i in fKeySet) {
                if (femaleMap[i]!!.lifeTime >= 400 || femaleMap[i]!!.childCount == 4) femaleMap.remove(i)
            }

            //createNewSpecies()
            moveSpecies()

            populationLifeSpan++

            //println("at = " + populationLifeSpan + " -> female = " + newSpeciesF + ", male = " + newSpeciesM +" , = "+(newSpeciesM.toFloat()/newSpeciesF.toFloat()))
            println("at = " + populationLifeSpan + " -> female = " + femaleMap.keys.size + ", male = " + maleMap.keys.size +" M = "+maleMap.values.count{it.freezeTime == -1}+" F = "+femaleMap.values.count{it.freezeTime == -1} +" , = "+(maleMap.keys.size.toFloat()/femaleMap.keys.size.toFloat()))

        }

    }


    private fun createNewSpecies() {

        val fKeys = femaleMap.keys.toSet()

        fKeys.forEach {

            val sp = femaleMap[it]

            if (sp != null) {
                if (sp.freezeTime != -1) {
                    if (sp.lifeTime - sp.freezeTime == 10) {

                        val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 0 else 1

                        if (gender == 0) {
                            maleMap[mCount] = Species(mCount++, sp.x, sp.y, 0, gender, -1, 0)
                        } else {
                            femaleMap[fCount] = Species(fCount++, sp.x, sp.y, 0, gender, -1, 0)
                        }

                    } else if (sp.lifeTime - sp.freezeTime == 20) {
                        femaleMap[it]?.freezeTime = -1
                    }
                }
            }

        }

        val mKeys = maleMap.keys.toSet()

        mKeys.forEach {
            if (maleMap[it]!!.lifeTime - maleMap[it]!!.freezeTime == 20) maleMap[it]!!.freezeTime = -1
        }

    }

    private fun moveSpecies() {

        maleMap.keys.forEach {

            val mSpecies = maleMap[it]

            if (mSpecies!!.freezeTime == -1 && mSpecies!!.lifeTime >= 200) {

                var closestFemaleSpeciesKey = Long.MAX_VALUE
                var closestFemaleSpeciesDistance = Double.MAX_VALUE

                femaleMap.keys.forEach { fit ->

                    if (femaleMap[fit]!!.freezeTime == -1 && femaleMap[fit]!!.lifeTime >= 200) {

                        val dist = sqrt(
                            (femaleMap[fit]!!.x - mSpecies!!.x).toDouble().pow(2)
                                    + (femaleMap[fit]!!.y - mSpecies!!.y).toDouble().pow(2)
                        )

                        if (dist <= closestFemaleSpeciesDistance) {
                            closestFemaleSpeciesDistance = dist
                            closestFemaleSpeciesKey = fit
                        }

                    }

                }

                if (closestFemaleSpeciesKey != Long.MAX_VALUE /*&& Random.nextInt(0, 100) % 4 == 0*/) {

                    maleMap[it]!!.freezeTime = maleMap[it]!!.lifeTime
                    femaleMap[closestFemaleSpeciesKey]!!.freezeTime = femaleMap[closestFemaleSpeciesKey]!!.lifeTime

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

            }


            femaleMap.keys.forEach { it1 ->

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

        }


    }

}