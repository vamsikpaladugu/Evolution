/*
* 1. x,y are physical position of a species.
* 2. lifeTime: Between 0 and 1000
* 3. gender: 0 Represents male and 1 Represents female
* 4. freezeTime: -1 represents species is not freeze, a species will freeze in reproduction process.
* */
data class Species(
    val id: Long,           // Unique id for this species
    var x: Int,             // x position in space
    var y: Int,             // y position in space
    var lifeTime: Int,      // Age of the species, Between 0 and 1000
    var gender: Int,        // Gender, 0 Represents male and 1 Represents female
    var freezeTime: Int,    // -1 represents species is not freeze, a species will freeze in reproduction process.
    var childCount: Int,    // Number of children, Max 4
    var isInLove: Boolean = false,   //
    val isItSpecial: Boolean = false,
    var canHaveSpecialChild: Boolean = false
)