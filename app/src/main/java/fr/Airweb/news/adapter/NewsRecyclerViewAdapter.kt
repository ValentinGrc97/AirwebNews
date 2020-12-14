package fr.Airweb.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.Airweb.news.R
import fr.Airweb.news.database.news.News
import fr.Airweb.news.ui.newsList.NewsListFragment
import kotlinx.android.synthetic.main.fragment_news_card.view.*

class NewsRecyclerViewAdapter(private val mValues: List<News>,
                              private val mListener: NewsListFragment.OnNewsListFragmentInteractionListener?)
    : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as News
            mListener?.onNewsListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.with(holder.mImage.context).load(mValues[position].picture).into(holder.mImage)
        holder.mTitle.text = mValues[position].title
        holder.mContent.text = mValues[position].content
        with(holder.mView) {
            tag = mValues[position]
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.count()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mImage: ImageView = mView.image
        val mTitle: TextView = mView.tv_title
        val mContent: TextView = mView.tv_content
    }
}