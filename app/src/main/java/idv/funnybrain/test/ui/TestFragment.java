package idv.funnybrain.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import idv.funnybrain.test.R;

/**
 * Created by Freeman on 2014/6/8.
 */
public class TestFragment extends Fragment
{
    int mNum;
    int mColor;
    String mName;

    static TestFragment newInstance(int num)
    {
        TestFragment f = new TestFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mColor = getArguments() != null ? getArguments().getInt("color") : Color.BLACK;
        mName = getArguments() != null ? getArguments().getString("name") : "no name";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.hello_world, container, false);
        View tv = v.findViewById(R.id.text);
        ((TextView)tv).setText("Fragment #" + mName);
        tv.setBackgroundColor(mColor);
        return v;
    }
}
