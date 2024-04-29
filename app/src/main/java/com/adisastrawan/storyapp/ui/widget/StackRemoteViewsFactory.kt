package com.adisastrawan.storyapp.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.database.StoryDao
import com.adisastrawan.storyapp.data.database.StoryEntity
import com.adisastrawan.storyapp.data.database.StoryRoomDatabase
import com.bumptech.glide.Glide
import kotlinx.coroutines.runBlocking

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory{
    private var mWidgetItems = listOf<StoryEntity>()
    private lateinit var storyDao: StoryDao
    override fun onCreate() {
        storyDao = StoryRoomDatabase.getInstance(context).storyDao()
        fetchListStory()
    }

    private fun fetchListStory() {
        runBlocking {
            mWidgetItems = storyDao.getAllStoryForWidget()
        }
    }

    override fun onDataSetChanged() {
        fetchListStory()
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        try {
            val bitmap: Bitmap = Glide.with(context.applicationContext)
                .asBitmap()
                .load(mWidgetItems[position].imageUrl)
                .submit()
                .get()
            rv.setImageViewBitmap(R.id.iv_detail_photo, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val extras = bundleOf(
            StoryWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.iv_detail_photo, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long =0

    override fun hasStableIds(): Boolean = false
}