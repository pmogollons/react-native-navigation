package com.reactnativenavigation.layouts;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.reactnativenavigation.params.ActivityParams;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.bridge.ReactContext;

public class LayoutFactory {
    public static Layout create(AppCompatActivity activity, ActivityParams params, ReactContext context) {
        switch (params.type) {
            case TabBased:
                return createBottomTabsScreenLayout(activity, params, context);
            case SingleScreen:
            default:
                return createSingleScreenLayout(activity, params, context);
        }
    }

    private static Layout createSingleScreenLayout(AppCompatActivity activity, ActivityParams params, ReactContext context) {
        String direction = "ltr";

        if (params != null && params.screenParams != null && params.screenParams.styleParams != null && params.screenParams.styleParams.direction != null) {
          direction = params.screenParams.styleParams.direction;
        }

        activity.getWindow().getDecorView().setLayoutDirection(direction.equals("rtl") ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();
        if(direction.equals("rtl")) {
          sharedI18nUtilInstance.allowRTL(context, true);
          sharedI18nUtilInstance.forceRTL(context, true);
        } else {
          sharedI18nUtilInstance.allowRTL(context, false);
          sharedI18nUtilInstance.forceRTL(context, false);
        }

        return new SingleScreenLayout(activity, params.leftSideMenuParams, params.rightSideMenuParams, params.screenParams);
    }

    private static Layout createBottomTabsScreenLayout(AppCompatActivity activity, ActivityParams params, ReactContext context) {
        if (params.tabParams.size() > 5) {
            removeAllButTheFirst5Tabs(params);
        }

        String direction = "ltr";

        if (params != null && params.tabParams != null && params.tabParams.get(0) != null) {
          direction = params.tabParams.get(0).styleParams.direction;
        }

        activity.getWindow().getDecorView().setLayoutDirection(direction.equals("rtl") ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);

        I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();
        if(direction.equals("rtl")) {
          sharedI18nUtilInstance.allowRTL(context, true);
          sharedI18nUtilInstance.forceRTL(context, true);
        } else {
          sharedI18nUtilInstance.allowRTL(context, false);
          sharedI18nUtilInstance.forceRTL(context, false);
        }

        return new BottomTabsLayout(activity, params);
    }

    private static void removeAllButTheFirst5Tabs(ActivityParams params) {
        Log.e("Navigation", "LayoutFactory:createBottomTabsScreenLayout() does not support more than 5 tabs, currently");
        while (params.tabParams.size() > 5) {
            params.tabParams.remove(params.tabParams.size() - 1);
        }
    }
}
