package com.hamdroid.StudentGuide.views.cell;

/**
 * Created by Eymen Hamrouni on 04/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import com.hamdroid.StudentGuide.R;
import com.hamdroid.StudentGuide.modele.Tuto;

import java.util.Objects;

/**
 * Created by kevindejesusferreira on 14/01/15.
 */
public class TutoCell extends RecyclerView.ViewHolder{

    Context context;
    TextView titre, sousTitre;
    ImageView image;
    Tuto item;

    public TutoCell(View itemView, Context context) {
        super(itemView);
        this.context = context;
        titre = (TextView) itemView.findViewById(R.id.element_liste_titre);
        sousTitre = (TextView) itemView.findViewById(R.id.element_liste_sous_titre);

        image = (ImageView) itemView.findViewById(R.id.element_liste_image);
    }



    String empty=  "http://www.supcom.mincom.tn/static/image/gif/px.gif";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void construire(Tuto item) {
        this.item = item;
        if (item.getTitre() != null)
            titre.setText(Html.fromHtml(Html.fromHtml(item.getTitre()).toString()));


        if (item.getDescription() != null)
           sousTitre.setText(Html.fromHtml(Html.fromHtml(item.getDescription()).toString()));
        else sousTitre.setVisibility(View.GONE);

      if (item.getUrl() != null) {
            Log.e("image", item.getUrl());
            Picasso.with(context).load(item.getUrl()).into(image);
            image.setVisibility(View.VISIBLE);

        }

      if(Objects.equals(item.getUrl(), empty))
            image.setImageResource(R.drawable.logo);
    }

}
