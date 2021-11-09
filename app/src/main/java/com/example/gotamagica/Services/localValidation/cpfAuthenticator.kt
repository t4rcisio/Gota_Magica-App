package com.example.gotamagica.Services.localValidation

class cpfAuthenticator(){

    fun checkCPF(cpf: String, level :Int = 0): Boolean{
        return try {
            if (cpf.any()) {

                if(cpf.length < 11 || cpf.length >11){
                    return false
                }

                if(!checkCpfHGelper(cpf)){
                    return false
                }

                var max: Int = 11 - level
                var end: Int = 1 + level

                var total: Int = 0
                var div: Int

                for (i in 0..(cpf.length - (end + 1))) {
                    //println("Total = $total + ${cpf[i].digitToInt()} * $max")
                    try {
                        total += cpf[i].digitToInt() * max
                    } catch (e: Exception) {
                        return false
                    }
                    max--
                }
                div = ((total * 10) % 11)

                if (div == 10)
                    div = 0
                //println(cpf[cpf.length -end])
                if (div == cpf[cpf.length - end].digitToInt() && level == 0) {
                    return checkCPF(cpf, 1)
                } else {
                    var temp = cpf[(cpf.length - (end))].digitToInt()
                    return div == temp
                }
            } else {
                return false
            }
            return false
        }catch (e: java.lang.Exception){

            return false
        }
    }

    private fun checkCpfHGelper(cpf: String): Boolean{
        var d1 = cpf[0]
        for(dig in cpf){
            if(d1 != dig){
                return true
            }
        }
        return false
    }

}