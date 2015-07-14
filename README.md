# AndroidGlitterView

A view to show bling bling stars when you touch it.

## Demo

![](arg/sample.gif)

## Dependency

```groovy
compile('com.liangfeizc:glitterview:1.0.0@aar')
```

## Attributes

|name|format|
|---|---|
|starColor|color|
|maxStarCount|integer|

## Usage

The assumed way to use `GlifferView` is put it above the content view. 

For example if you want to draw bling bling stars on an ImageView like the demo above, you xml file would be like this.

*Lay a `GlifferView` above an `ImageView`*:

```xml
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:glitter="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerInside"
        android:src="@drawable/your_image" />

    <com.liangfeizc.glitterview.GlitterView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        glitter:starColor="#ED2727"
        glitter:maxStarCount="250" />

</FrameLayout>
```

## License

    The MIT License (MIT)
    
