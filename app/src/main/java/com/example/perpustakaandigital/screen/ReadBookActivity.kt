package com.example.perpustakaandigital.screen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.perpustakaandigital.R
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.activity_read_book.*

//import com.pdfview.PDFView
class ReadBookActivity : AppCompatActivity(), DownloadFile.Listener {
    var file: String? = null
    var root: LinearLayout? = null
    var remotePDFViewPager: RemotePDFViewPager? = null
    var etPdfUrl: EditText? = null
    var btnDownload: Button? = null
    var adapter: PDFPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        file = intent.getStringExtra("FILE")
        file = file.toString().trim { it <= ' ' }
        setTitle("remote_pdf_example")
        setContentView(R.layout.activity_read_book)
        root = findViewById(R.id.remote_pdf_root)
        etPdfUrl = findViewById(R.id.et_pdfUrl)
        btnDownload = findViewById(R.id.btn_download)
        setDownloadButtonListener()

        btn_read_back.setOnClickListener {
            finish()
        }
    }

    protected override fun onDestroy() {
        super.onDestroy()
        if (adapter != null) {
            adapter!!.close()
        }
    }

    protected fun setDownloadButtonListener() {
        val ctx: Context = this
        val listener: DownloadFile.Listener = this
        remotePDFViewPager = RemotePDFViewPager(ctx, file, listener)
        remotePDFViewPager!!.id = R.id.pdfViewPager
        //hideDownloadButton()
    }

    protected fun getUrlFromEditText(): String {
        return etPdfUrl!!.text.toString().trim { it <= ' ' }
    }

    fun showDownloadButton() {
        btnDownload!!.visibility = View.VISIBLE
    }

    fun hideDownloadButton() {
        btnDownload!!.visibility = View.INVISIBLE
    }

    fun updateLayout() {
        root!!.removeAllViewsInLayout()
//        root!!.addView(
//            etPdfUrl,
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        root!!.addView(
//            btnDownload,
//            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
//        )
        root!!.addView(
            remotePDFViewPager,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager!!.adapter = adapter
        updateLayout()
        showDownloadButton()
    }

    override fun onFailure(e: Exception) {
        e.printStackTrace()
        showDownloadButton()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {}
}



//class ReadBookActivity : AppCompatActivity(), DownloadFile.Listener{
//    lateinit var btn_back : ImageView
//    var remotePDFViewPager: RemotePDFViewPager? = null
//    var etPdfUrl: EditText? = null
//    var btnDownload: Button? = null
//    var pdfView : PDFViewPager? = null
//    var adapter: PDFPagerAdapter? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_read_book)
//
//        setDownloadButtonListener()
//
//        btn_back = findViewById(R.id.btn_read_back)
//        pdfView = findViewById(R.id.pdfView)
//
//        //read_title = findViewById(R.id.tv_read_title)
//        btn_back.setOnClickListener {
//            finish()
//        }
////        val listener: DownloadFile.Listener = this
////        remotePDFViewPager = RemotePDFViewPager(this, getUrlFromEditText(), listener)
////        remotePDFViewPager!!.id = R.id.pdfView
//
//        //displayFromAsset(SAMPLE_FILE)
//    }
//
//    protected fun setDownloadButtonListener() {
//        val ctx: Context = this
//        val listener: DownloadFile.Listener = this
//        remotePDFViewPager = RemotePDFViewPager(ctx, "https://firebasestorage.googleapis.com/v0/b/perpustakaan-digital-37f9f.appspot.com/o/books%2F0060543957?alt=media&token=4c2c0d68-8b90-4cf7-a3ae-11432e687600", listener)
//        remotePDFViewPager!!.id = R.id.pdfView
////            hideDownloadButton()
//    }
//
//    protected fun getUrlFromEditText(): String? {
//        return etPdfUrl!!.text.toString().trim { it <= ' ' }
//    }
//
//    fun showDownloadButton() {
//        btnDownload!!.visibility = View.VISIBLE
//    }
//
//    fun hideDownloadButton() {
//        btnDownload!!.visibility = View.INVISIBLE
//    }
//
//    override fun onSuccess(url: String?, destinationPath: String?) {
//        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
//        remotePDFViewPager!!.adapter = adapter
//        //updateLayout()
//        //showDownloadButton()
//    }
//
//    override fun onFailure(e: Exception) {
//        e.printStackTrace()
//        //showDownloadButton()
//    }
//
//    override fun onProgressUpdate(progress: Int, total: Int) {}
//
//
////
////    private fun displayFromAsset(assetFileName: String) {
////        pdfFileName = assetFileName
////
////        pdfView!!.fromAsset("paper.pdf")
////            .defaultPage(pageNumber)
////            .onPageChange(this)
////            .enableAnnotationRendering(true)
////            .onLoad(this)
////            .scrollHandle(DefaultScrollHandle(this))
////            .spacing(10) // in dp
////            .onPageError(this)
////            .load()
////    }
////
////    override fun onPageChanged(page: Int, pageCount: Int) {
////        pageNumber = page
////        read_title.text = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
////    }
////
////    override fun loadComplete(nbPages: Int) {
////        val meta = pdfView!!.documentMeta
////        Log.e(TAG, "title = " + meta.title)
////        Log.e(TAG, "author = " + meta.author)
////        Log.e(TAG, "subject = " + meta.subject)
////        Log.e(TAG, "keywords = " + meta.keywords)
////        Log.e(TAG, "creator = " + meta.creator)
////        Log.e(TAG, "producer = " + meta.producer)
////        Log.e(TAG, "creationDate = " + meta.creationDate)
////        Log.e(TAG, "modDate = " + meta.modDate)
////        printBookmarksTree(pdfView!!.tableOfContents, "-")
////    }
////
////    fun printBookmarksTree(
////        tree: List<Bookmark>,
////        sep: String
////    ) {
////        for (b in tree) {
////            Log.e(TAG,
////                String.format("%s %s, p %d", sep, b.title, b.pageIdx)
////            )
////            if (b.hasChildren()) {
////                printBookmarksTree(b.children, "$sep-")
////            }
////        }
////    }
////
////    override fun onPageError(page: Int, t: Throwable?) {
////        Log.e(TAG,
////            "Cannot load page $page"
////        )
////    }
//
//}