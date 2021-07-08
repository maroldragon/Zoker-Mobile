package com.example.perpustakaandigital.screen

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.perpustakaandigital.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.shockwave.pdfium.PdfDocument.Bookmark
import kotlinx.android.synthetic.main.activity_read_book.*

//import com.pdfview.PDFView

class ReadBookActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener,
    OnPageErrorListener {
    lateinit var btn_back : ImageView

    val TAG: String = ReadBookActivity::class.java.getSimpleName()
    val REQUEST_CODE = 42
    val PERMISSION_CODE = 42042
    val SAMPLE_FILE = "great-expectations.pdf"
    var pdfView: PDFView? = null
    val uri: Uri? = null
    var pageNumber = 0
    var pdfFileName: String? = null
    lateinit var read_title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        btn_back = findViewById(R.id.btn_read_back)
        pdfView = findViewById(R.id.pdfView)

        read_title = findViewById(R.id.tv_read_title)
        btn_back.setOnClickListener {
            finish()
        }

        displayFromAsset(SAMPLE_FILE)
    }

    private fun displayFromAsset(assetFileName: String) {
        pdfFileName = assetFileName

        pdfView!!.fromAsset("paper.pdf")
            .defaultPage(pageNumber)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .load()
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
        read_title.text = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
    }

    override fun loadComplete(nbPages: Int) {
        val meta = pdfView!!.documentMeta
        Log.e(TAG, "title = " + meta.title)
        Log.e(TAG, "author = " + meta.author)
        Log.e(TAG, "subject = " + meta.subject)
        Log.e(TAG, "keywords = " + meta.keywords)
        Log.e(TAG, "creator = " + meta.creator)
        Log.e(TAG, "producer = " + meta.producer)
        Log.e(TAG, "creationDate = " + meta.creationDate)
        Log.e(TAG, "modDate = " + meta.modDate)
        printBookmarksTree(pdfView!!.tableOfContents, "-")
    }

    fun printBookmarksTree(
        tree: List<Bookmark>,
        sep: String
    ) {
        for (b in tree) {
            Log.e(TAG,
                String.format("%s %s, p %d", sep, b.title, b.pageIdx)
            )
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.e(TAG,
            "Cannot load page $page"
        )
    }

}