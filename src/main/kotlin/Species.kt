/*
* 1. x,y are physical position of a species.
* 2. lifeTime: Between 0 and 1000
* 3. gender: 0 Represents male and 1 Represents female
* 4. freezeTime: -1 represents species is not freeze, a species will freeze in reproduction process.
* */
data class Species(var x: Int, var y: Int, var lifeTime: Int, var gender: Int, var freezeTime:Int)