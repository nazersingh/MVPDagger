package com.nazer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nazer.R;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.diskCacheStrategyOf;

public class Utility {

    /*
        Load Pic on ImageView
         */
    public void loadPic(final Context context, String file, final ImageView abc, final ProgressBar progressBar,final boolean isRound) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            RequestBuilder<Bitmap> requestBuilder;
            if (isRound) {
//                center crop
                requestBuilder = Glide.with(context).asBitmap().load(file)
                        .apply(RequestOptions.centerCropTransform())
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in));
            } else {
                //fit center
                requestBuilder = Glide.with(context).asBitmap().load(file)
                        .apply(RequestOptions.fitCenterTransform())
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in));
            }

            requestBuilder.listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    abc.setImageResource(R.mipmap.ic_launcher);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(new BitmapImageViewTarget(abc) {
                @Override
                protected void setResource(Bitmap resource) {
                    if (isRound) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);

                        abc.setImageDrawable(circularBitmapDrawable);
                    } else {
                        abc.setImageBitmap(resource);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPic(final Context context, String file, final ImageView imageView )
    {
        try {
            PrintLog.e("blur pic ", "" + file);
            Glide.with(context)
                    .asBitmap()
                    .load(file)
                    .apply(centerCropTransform())
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .apply(diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
