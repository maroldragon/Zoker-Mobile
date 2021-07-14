package com.example.perpustakaandigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.perpustakaandigital.model.Book
import com.example.perpustakaandigital.R
import com.example.perpustakaandigital.model.Peminjaman
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RiwayatAdapter(val pinjamList: ArrayList<Peminjaman>) : RecyclerView.Adapter<RiwayatAdapter.Holder>() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbRef: DatabaseReference = database.reference

    private lateinit var onItemClickCallback: RiwayatAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pinjam = pinjamList[position]
        generateBook(pinjam.idBuku, holder)

        holder.tanggal.text = pinjam.tanggal
        holder.pinjam_lagi.visibility = View.GONE
        holder.pinjam_lagi.setOnClickListener { onItemClickCallback.onItemClicked(pinjamList[holder.adapterPosition]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.riwayat_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return pinjamList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgPhoto: ImageView = itemView!!.findViewById(R.id.imgv_riwayat)
        var bookName: TextView = itemView!!.findViewById(R.id.tv_riwayat_name)
        var bookAuthor : TextView = itemView!!.findViewById(R.id.tv_riwayat_author)
        var pinjam_lagi : Button = itemView!!.findViewById(R.id.btn_riwayat_pinjamLagi)
        var tanggal : TextView = itemView!!.findViewById(R.id.tv_riwayat_tanggal)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Peminjaman)
    }

    fun generateBook(idBuku: String?, holder: Holder) {
        val query: Query = dbRef.child("books").orderByChild("isbn").equalTo(idBuku)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (p in p0.children){
                        val book = p.getValue(Book::class.java)
                        Glide.with(holder.itemView.context)
                            .load(book?.cover)
                            .centerCrop()
                            .placeholder(R.drawable.no_image)
                            .into(holder.imgPhoto)

                        holder.bookName.text = book?.judul
                        holder.bookAuthor.text = book?.penulis
                    }
                }
            }
        })
    }

}