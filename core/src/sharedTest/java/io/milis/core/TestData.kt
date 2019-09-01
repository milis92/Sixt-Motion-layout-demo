package io.milis.core

import io.milis.sixt.core.domain.services.entities.Car

object TestData {

    val car1: Car = Car("WMWSU31070T077232",
            "mini",
            "MINI",
            "Regine",
            "BMW",
            "MINI",
            "midnight_black",
            "MINI",
            "P",
            0.55,
            "M",
            "M-I7425",
            48.114988,
            11.598359,
            "CLEAN",
            "https://cdn.sixt.io/codingtask/images/mini.png")

    val car2: Car = Car(
            "WMWSW31060T222495",
            "mini",
            "MINI",
            "Angie",
            "BMW",
            "MINI",
            "midnight_black",
            "MINI",
            "D",
            0.4,
            "M",
            "M-VO0244",
            48.152207,
            11.572649,
            "CLEAN",
            "https://cdn.sixt.io/codingtask/images/mini.png"
    )

    val cars = listOf(car1, car2)
}