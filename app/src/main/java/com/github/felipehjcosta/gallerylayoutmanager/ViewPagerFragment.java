package com.github.felipehjcosta.gallerylayoutmanager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.felipehjcosta.gallerylayoutmanager.adapter.ImageCardAdapter;
import com.github.felipehjcosta.gallerylayoutmanager.base.BaseRestoreFragment;
import com.github.felipehjcosta.gallerylayoutmanager.layout.impl.CurveTransformer;
import com.github.felipehjcosta.gallerylayoutmanager.util.BitmapUtils;
import com.github.felipehjcosta.gallerylayoutmanager.util.FastBlur;
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager;

public class ViewPagerFragment extends BaseRestoreFragment {
    @BindView(com.github.felipehjcosta.gallerylayoutmanager.R.id.pager_bg)
    ImageView mPagerBg;
    @BindView(com.github.felipehjcosta.gallerylayoutmanager.R.id.pager_recycle_view)
    FlingRecycleView mPagerRecycleView;
    List<Integer> mResId;

    public static ViewPagerFragment newInstance() {
        Bundle args = new Bundle();
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Generate by live templates.
     * Use FragmentManager to find this Fragment's instance by tag
     */
    public static ViewPagerFragment findFragment(FragmentManager manager) {
        return (ViewPagerFragment) manager.findFragmentByTag(ViewPagerFragment.class.getSimpleName());
    }

    List<ImageCardAdapter.CardItem> mCardItems;

    {
        mResId = new ArrayList<Integer>(4);
        mResId.add(com.github.felipehjcosta.gallerylayoutmanager.R.drawable.img1);
        mResId.add(com.github.felipehjcosta.gallerylayoutmanager.R.drawable.img2);
        mResId.add(com.github.felipehjcosta.gallerylayoutmanager.R.drawable.img3);
        mResId.add(com.github.felipehjcosta.gallerylayoutmanager.R.drawable.img4);
        mCardItems = new ArrayList<ImageCardAdapter.CardItem>(50);
        ImageCardAdapter.CardItem cardItem;
        for (int i = 0; i < 50; i++) {
            cardItem = new ImageCardAdapter.CardItem(mResId.get(i % mResId.size()), "item:" + i);
            mCardItems.add(cardItem);
        }
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.github.felipehjcosta.gallerylayoutmanager.R.layout.fragment_view_pager, container, false);
    }

    @Override
    protected void initView(View root, Bundle savedInstanceState) {
        ButterKnife.bind(this, root);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPagerRecycleView.setFlingAble(false);
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mPagerRecycleView, 30);
//        layoutManager.attach(mPagerRecycleView);
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                Bitmap bmp = BitmapUtils.decodeSampledBitmapFromResource(getResources(), mResId.get(position % mResId.size()), 100, 100);
                mPagerBg.setImageBitmap(FastBlur.doBlur(bmp, 20, false));
            }
        });
        layoutManager.setItemTransformer(new CurveTransformer());
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        ImageCardAdapter imageAdapter = new ImageCardAdapter(mCardItems, (int) (displayMetrics.widthPixels * 0.7f), (int) (displayMetrics.heightPixels * 0.8f));
        imageAdapter.setOnItemClickListener(new ImageCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "click" + mCardItems.get(position).mName, Toast.LENGTH_SHORT).show();
                mPagerRecycleView.smoothScrollToPosition(position);
            }
        });
        mPagerRecycleView.setAdapter(imageAdapter);
    }


}
