package com.skymobi.signatures;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skymobi.mopoui.binding.Binding;

import java.util.Arrays;

/**
 * Created by wind.zhang on 14-3-17.
 */
public class HexFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_SUBTITLE = "subtitle";
    private static final String ARG_BYTES = "bytes";

    private String mTitle;
    private String mSubtitle;
    private byte[] mBytes;

    private TextView mTitleView;
    private TextView mSubtitleView;
    private TextView mLengthView;
    private TextView mContentView;

    public static HexFragment newInstance(String title, String subtitle, byte[] bytes) {
        HexFragment fragment = new HexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUBTITLE, subtitle);
        args.putByteArray(ARG_BYTES, bytes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        mTitle = args.getString(ARG_TITLE);
        mSubtitle = args.getString(ARG_SUBTITLE);
        mBytes = args.getByteArray(ARG_BYTES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hex, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleView = (TextView) view.findViewById(R.id.title);
        mSubtitleView = (TextView) view.findViewById(R.id.subtitle);
        mLengthView = (TextView) view.findViewById(R.id.length);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setText(mTitle);
        mSubtitleView.setText(mSubtitle);
        mLengthView.setText(Integer.toString(mBytes.length));
        mContentView.setText(Binding.bytesToHex(mBytes));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.hex, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_hex) {
            if (getString(R.string.action_bytes).equals(item.getTitle())) {
                item.setIcon(R.drawable.ic_action_hex);
                item.setTitle(R.string.action_hex);
                String text = Arrays.toString(mBytes);
                mContentView.setText(text.substring(1, text.length() - 1));
            } else {
                item.setIcon(R.drawable.ic_action_bytes);
                item.setTitle(R.string.action_bytes);
                mContentView.setText(Binding.bytesToHex(mBytes));
            }
        }
        return true;
    }
}
