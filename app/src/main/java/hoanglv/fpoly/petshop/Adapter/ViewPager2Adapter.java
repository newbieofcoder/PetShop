package hoanglv.fpoly.petshop.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hoanglv.fpoly.petshop.fragment.CartFragment;
import hoanglv.fpoly.petshop.fragment.CategoryFragment;
import hoanglv.fpoly.petshop.fragment.FavoriteFragment;
import hoanglv.fpoly.petshop.fragment.HomeFragment;


public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CartFragment();
            case 2:
                return new CategoryFragment();
            case 3:
                return new FavoriteFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
