package com.revton.virtualfitting.model

class ClothesModel(id:String?,file_name:String?) {


    private var id: String
    private var file_name: String

    init {
        this.id = id!!
        this.file_name = file_name!!
    }
    fun getId(): String? {
        return id
    }
    fun setId(id: String?) {
        this.id = id!!
    }
    fun getFile(): String? {
        return file_name
    }
    fun setFile(file_name: String?) {
        this.file_name = file_name!!
    }

}