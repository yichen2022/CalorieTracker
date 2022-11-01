package com.example.calorietracker.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import java.io.File

class Storage {
    private val foodStorage: StorageReference = FirebaseStorage.getInstance().reference.child("foods")
    fun uploadImage(localFile: File, uuid: String, uploadSuccess:()->Unit) {
        val file = Uri.fromFile(localFile)
        val uuidRef = foodStorage.child(uuid)
        val metadata = StorageMetadata.Builder().setContentType("image/jpg").build()
        val uploadTask = uuidRef.putFile(file, metadata)
        uploadTask
            .addOnFailureListener {
                // Handle unsuccessful uploads
                if(localFile.delete()) {
                    Log.d(javaClass.simpleName, "Upload FAILED $uuid, file deleted")
                } else {
                    Log.d(javaClass.simpleName, "Upload FAILED $uuid, file delete FAILED")
                }
            }
            .addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                uploadSuccess()
                if(localFile.delete()) {
                    Log.d(javaClass.simpleName, "Upload succeeded $uuid, file deleted")
                } else {
                    Log.d(javaClass.simpleName, "Upload succeeded $uuid, file delete FAILED")
                }
            }
    }
}