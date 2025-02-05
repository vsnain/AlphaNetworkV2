package com.alpha.alphanetwork.Dark;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alpha.alphanetwork.Model.ModelAnonymousFeed;

import androidx.fragment.app.FragmentManager;

//import android.support.v4.view.PagerAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.alpha.alphanetwork.Home.Home;
import com.alpha.alphanetwork.R;

import Utils.LikesToggle;



public class DarkAdapter extends RecyclerView.Adapter<DarkAdapter.MyViewHolder>{

    private List<ModelAnonymousFeed> posts;
    private Context context;
    private FragmentManager fragmentManager;
    private static final String TAG = "Adapter";






    public DarkAdapter(List<ModelAnonymousFeed> posts, Context context,FragmentManager fragmentManager) {
        this.posts = posts;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_darkfeeditem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        final ModelAnonymousFeed modelFeed = posts.get(position);


        holder.tv_likes.setText(String.valueOf(modelFeed.getLikes()));

        holder.tv_status.setText(modelFeed.getTitle());
        holder.tv_comments.setText(String.valueOf(modelFeed.getComments()));


    }

    @Override
    public int getItemCount() {

        return posts.size();

    }





    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic, imgView_postPic, imgView_comments, imgView_back;

        ImageView imgView_like, imgView_liked, imgView_dislike, imgView_disliked;
        private GestureDetector lGestureDetector;
        private GestureDetector dGestureDetector;
        private LikesToggle like;



        public MyViewHolder(View itemView) {

            super(itemView);


            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_likes = (TextView) itemView.findViewById(R.id.tv_likescount);


            tv_comments = (TextView) itemView.findViewById(R.id.tv_comments);
            imgView_comments = itemView.findViewById(R.id.speech_bubble);
            imgView_back = itemView.findViewById(R.id.backArrow);

            tv_status = (TextView) itemView.findViewById(R.id.tv_status);


            //likes toggling
            imgView_like = (ImageView) itemView.findViewById(R.id.image_like);
            imgView_liked= (ImageView) itemView.findViewById(R.id.image_liked);
            imgView_dislike = (ImageView) itemView.findViewById(R.id.image_dislike);
            imgView_disliked = (ImageView) itemView.findViewById(R.id.image_disliked);
            lGestureDetector = new GestureDetector(context,new lGestureListener());
            dGestureDetector = new GestureDetector(context,new dGestureListener());


            imgView_liked.setVisibility(View.GONE);
            imgView_like.setVisibility(View.VISIBLE);
            imgView_disliked.setVisibility(View.GONE);
            imgView_dislike.setVisibility(View.VISIBLE);






            like = new LikesToggle(imgView_like,imgView_liked,imgView_dislike,imgView_disliked);
            likeToggle();
            dislikeToggle();




            //comments

            tv_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String id = posts.get(position).getPostid();

                    ((Home)context).onCommentThreadSelected(id,"anony");

                    //going to need to do something else?
                    ((Home)context).hideLayout();

                }
            });


            imgView_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String id = posts.get(position).getPostid();
                    ((Home)context).onCommentThreadSelected(id,"anony");

                    //going to need to do something else?
                    ((Home)context).hideLayout();

                }
            });


        }





        private void likeToggle(){

            imgView_liked.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return lGestureDetector.onTouchEvent(event);
                }
            });


            imgView_like.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "Entered like touch listerner");
                    return lGestureDetector.onTouchEvent(event);
                }
            });


        }



        private void dislikeToggle(){
            imgView_disliked.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return dGestureDetector.onTouchEvent(event);
                }
            });


            imgView_dislike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "Entered dislike touch listerner");
                    return dGestureDetector.onTouchEvent(event);
                }
            });
        }



        public class lGestureListener extends GestureDetector.SimpleOnGestureListener{



            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int position = getAdapterPosition();
                String id = posts.get(position).getPostid();
                like.toggleLike(id,"anonymous");
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                int position = getAdapterPosition();
                String id = posts.get(position).getPostid();
                like.toggleLike(id,"anonymous");
                return true;
            }




        }

        public class dGestureListener extends GestureDetector.SimpleOnGestureListener{

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int position = getAdapterPosition();
                String id = posts.get(position).getPostid();
                like.toggleDisLike(id,"anonymous");
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                int position = getAdapterPosition();
                String id = posts.get(position).getPostid();
                like.toggleDisLike(id,"anonymous");
                return true;
            }


        }



    }


}


