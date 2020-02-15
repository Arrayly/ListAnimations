package project.recyclerviewanimations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.List;

public class AdapterListAnimation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Context which this adapter is used in
    private Context mContext;

    //The animation type we want to use
    private int animation_type;

    //Our data to be shown
    private List<Data> mDataList;

    //true only when the data is still being fetched/prepared
    public boolean showShimmer = true;

    //The number of items we want to be shown shimmering
    private int shimmer_item_number = 7;


    public AdapterListAnimation(Context context, List<Data> dataList, int animation_type) {
        this.mDataList = dataList;
        mContext = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public TextView description;

        public ShimmerFrameLayout mShimmerFrameLayout;


        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            description = v.findViewById(R.id.description);
            mShimmerFrameLayout = v.findViewById(R.id.root_layout);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            if (showShimmer && view.mShimmerFrameLayout != null) {
                view.mShimmerFrameLayout.startShimmer();
            } else {
                view.mShimmerFrameLayout.stopShimmer();
                view.mShimmerFrameLayout.setShimmer(null);
                Data data = mDataList.get(position);
                view.image.setBackgroundResource(data.getImageId());
                view.description.setText(data.getLoremIpsum());

                //Turn back textView background to transparent once the animation is finished
                view.description.setBackgroundResource(android.R.color.transparent);

                setAnimation(view.itemView, position);
            }


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //When the user scrolls down, we change the translation of the animation
                //This will make it look much better.
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        //if showShimmer = true, return shimmer_item_number, else, return our data size.
        return showShimmer ? shimmer_item_number : mDataList.size();
    }


    private int lastPosition = -1;

    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            //When we scroll down, on_attach will become false and so we return -1
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);

            lastPosition = position;
        }
    }

}
