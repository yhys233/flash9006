package com.flash9006.stars

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore

object FileUriHelper {
    fun getRealPath(context: Context, uri: Uri): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return context.getExternalFilesDir(null)?.parentFile?.parentFile?.parentFile?.path + "/" + split[1]
                }
            }
        }

        if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        }

        if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        return try {
            context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                } else null
            }
        } catch (e: Exception) {
            null
        }
    }
}
