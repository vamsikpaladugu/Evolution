/*
* Gender Arrays: two arrays each for one gender
* Space matrix: a 2D matrices with the size of space
* populationLifeSpan: Total life span of simulator
* */
//class TimeLaps {
//
//    private var populationLifeSpan = 0
//
//    private val zeroGender = mutableListOf<Species>()
//    private val oneGender = mutableListOf<Species>()
//
//    /*
//    * create 200 species with Random positions, gender and lifespan
//    * */
//    fun init() {
//
//        for (i in 0..200) {
//
//            val gender = if (Random.nextInt(1, 101) <= maleSpeciesPerHundred) 1 else 0
//
//            val posX = Random.nextInt(0, SpaceX)
//            val posY = Random.nextInt(0, SpaceX)
//
//            val lifeTime = Random.nextInt(200, 600)
//
//            val species = Species(posX, posY, lifeTime, gender, -1)
//
//            if (gender == 1) oneGender.add(species) else zeroGender.add(species)
//
//        }
//
//        snap()
//
//    }
//
//
//    /*
//    * This function will run repeatedly until population collapse
//    * 1. go throw oneGender matrix and check if any species is freeze for 10 lifespan steps then
//    *    create a new species and the gender of new species will be calculated with random number generator
//    *    if any species is freeze for 20 lifespan steps then unfreeze the species.
//    * 2. Remove species if their life span is 1000
//    * 2. Each species will be moved to one step in random direction and Compute the new positions of all the species.
//    * 3. Create a new space matrix and check if any zeroGender Species with lifetime between 200 and 600 are present in each location
//    *    and mark the locations to true
//    * 4. go throw all active oneGender species and check if any species position has true value in the space matrix
//    *    if yes freeze that species.
//    * */
//    private fun snap() {
//
//        while (oneGender.size != 0 && zeroGender.size != 0) {
//            createNewSpecies()
//            moveSpecies()
//
//            val zSize = zeroGender.size
//
//            zeroGender.removeAll{it.lifeTime >= 1000}
//
//            print(" z = "+(zSize - zeroGender.size)+" ")
//
//            oneGender.removeAll{it.lifeTime >= 1000}
//
//
//            val spaceMatrix = Array(SpaceX) { BooleanArray(SpaceX) };
//
//            zeroGender.forEach {
//                spaceMatrix[it.x][it.y] = it.lifeTime in 201..599
//            }
//
//            for (i in 0 until oneGender.size) {
//                if (oneGender[i].freezeTime == -1 && spaceMatrix[oneGender[i].x][oneGender[i].y]) {
//                    oneGender[i].freezeTime = oneGender[i].lifeTime
//                }
//            }
//
//            for (i in 0 until zeroGender.size) {
//                zeroGender[i].lifeTime++
//            }
//            for (i in 0 until oneGender.size) {
//                oneGender[i].lifeTime++
//            }
//
//            populationLifeSpan++;
//
//            println("at = "+populationLifeSpan+" -> zero = "+zeroGender.size +", one = "+oneGender.size)
//
//
//        }
//
//    }
//
//    private fun moveSpecies() {
//
//        for (i in 0 until oneGender.size) {
//
//            var isItGoodPosition = false;
//
//            var x = oneGender[i].x
//            var y = oneGender[i].y
//
//            while (!isItGoodPosition) {
//
//                val direction = Random.nextInt(0, 4)
//
//                if (direction == 0 && x - 1 >= 0) {
//                    x -= 1;
//                    isItGoodPosition = true
//                } else if (direction == 1 && x + 1 < SpaceX) {
//                    x += 1;
//                    isItGoodPosition = true
//                } else if (direction == 2 && y - 1 >= 0) {
//                    y -= 1;
//                    isItGoodPosition = true
//                } else if (direction == 3 && y + 1 < SpaceX) {
//                    y += 1;
//                    isItGoodPosition = true
//                }
//
//            }
//            oneGender[i].x = x
//            oneGender[i].y = y
//        }
//
//        for (i in 0 until zeroGender.size) {
//
//            var isItGoodPosition = false;
//
//            var x = zeroGender[i].x
//            var y = zeroGender[i].y
//
//            while (!isItGoodPosition) {
//
//                val direction = Random.nextInt(0, 4)
//
//                if (direction == 0 && x - 1 >= 0) {
//                    x -= 1;
//                    isItGoodPosition = true
//                } else if (direction == 1 && x + 1 < SpaceX) {
//                    x += 1;
//                    isItGoodPosition = true
//                } else if (direction == 2 && y - 1 >= 0) {
//                    y -= 1;
//                    isItGoodPosition = true
//                } else if (direction == 3 && y + 1 < SpaceX) {
//                    y += 1;
//                    isItGoodPosition = true
//                }
//
//            }
//
//            zeroGender[i].x = x
//            zeroGender[i].y = y
//
//
//        }
//
//
//    }
//
//    private fun createNewSpecies() {
//
//        for (i in 0 until oneGender.size) {
//            if (oneGender[i].freezeTime != -1) {
//                if (oneGender[i].lifeTime - oneGender[i].freezeTime == 10) {
//                    val gender = if (Random.nextInt(1, 101) <= zeroGenderSpeciesPerHundred) 1 else 0
//                    val species = Species(oneGender[i].x, oneGender[i].y, 0, gender, -1)
//                    if (gender == 1) oneGender.add(species) else zeroGender.add(species)
//                } else if (oneGender[i].lifeTime - oneGender[i].freezeTime == 20) {
//                    oneGender[i].freezeTime = -1
//                }
//            }
//        }
//
//    }
//
//}