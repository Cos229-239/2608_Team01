package com.team01.steamanalyst.components

import java.util.Locale


fun formatUsd(value: Double) : String{
    return "$" + String.format(Locale.US, "%,.2f", value)
}