package com.example.buildProduct.remoteUtils

class WebServiceHolder {

    private var webservice: WebService? = null

    fun setAPIService(webservice: WebService) {
        this.webservice = webservice
    }

    companion object {
        private var webServiceHolder: WebServiceHolder? = null

        val instance: WebServiceHolder
            get() {
                if (webServiceHolder == null) {
                    webServiceHolder = WebServiceHolder()
                }
                return webServiceHolder!!
            }
    }
}