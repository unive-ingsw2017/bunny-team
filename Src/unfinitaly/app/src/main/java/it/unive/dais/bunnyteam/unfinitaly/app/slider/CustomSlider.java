package it.unive.dais.bunnyteam.unfinitaly.app.slider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 *
 * @author BunnyTeam, Università Ca' Foscari
 */
public class CustomSlider extends Fragment {

    protected static final String ARG_LAYOUT_RES_ID = "layoutResId";
    protected int layoutResId;
    private View view;
    public static CustomSlider newInstance(int layoutResId) {
        CustomSlider sampleSlide = new CustomSlider();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);


        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
