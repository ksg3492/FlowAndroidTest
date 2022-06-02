package com.sunggil.flowandroidtest.data

object Name {
    init {
        System.loadLibrary("native-lib")
    }

    /**
     * id
     * @return
     */
    external fun id() : String

    /**
     * key
     * @return
     */
    external fun key() : String
}