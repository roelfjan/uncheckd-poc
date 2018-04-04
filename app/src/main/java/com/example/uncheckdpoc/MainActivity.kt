package com.example.uncheckdpoc

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.content.Intent

import org.jetbrains.anko.sdk25.coroutines.onClick
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import android.R.attr.bitmap
import com.google.android.gms.vision.Frame
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.google.android.gms.vision.text.TextRecognizer
import android.util.SparseArray
import android.R.attr.y
import android.R.attr.x
import com.google.android.gms.vision.face.Landmark
import android.util.Log;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.onClick {
            EasyImage.openChooserWithGallery(this@MainActivity, resources.getString(R.string.pick_image_intent_text), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object: DefaultCallback() {
            override fun onImagesPicked(imageFiles: MutableList<File>, source: EasyImage.ImageSource?, type: Int) {
                gotoUploadScreen(imageFiles.get(0))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun gotoUploadScreen(file: File) {
        val context = applicationContext

        // https@ //developers.google.com/vision/android/detect-faces-tutorial
        val bitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
        val frame = Frame.Builder().setBitmap(bitmap).build()
        val textRecognizer = TextRecognizer.Builder(context).build();

        val textBlocks = textRecognizer.detect(frame)

        val allLines: ArrayList<String> = java.util.ArrayList<String>();

        for (i in 0 until textBlocks.size()) {
            val textBlock = textBlocks.valueAt(i)

            val lines = textBlock.getComponents();

            for (j in 0 until lines.size) {
                var line = lines[j];
                var x = line.value;

                allLines.add(x);
            }
        }

        startActivity(foundbeers.createIntent(this, allLines))
    }
}
