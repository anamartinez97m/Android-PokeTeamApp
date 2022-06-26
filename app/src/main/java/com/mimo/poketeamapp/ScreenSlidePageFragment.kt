package com.mimo.poketeamapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class ScreenSlidePageFragment(val drawable: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inf = inflater.inflate(R.layout.fragment_screen_slide_page_tutorial, container, false)

        val image = inf.findViewById<ImageView>(R.id.picture)
        Picasso.get().load(drawable).into(image)

        return inf

    }
}
