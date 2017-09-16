# GalleryLayoutManager

A custom LayoutManager to build a Gallery or a ViewPager like RecyclerView that shows items in a center-locked and support both HORIZONTAL and VERTICAL scroll.And View Recycle Machine is also supported.

## Screenshots

![ViewPager](./screenshots/ViewPager.gif)

![Demo](./screenshots/demo.gif)

## Usage

### 1、Build

#### Gradle

```java
compile 'com.felipecosta:GalleryLayoutManager:{lastest-release-version}'
```

> Be care ：if you have used `RecyclerView` in your project , maybe your should use this library as below and your recyclerview-v7 requires API level 24.2.0 or higher

```java
compile ('com.felipecosta:GalleryLayoutManager:{lastest-release-version}'){
    exclude group: 'com.android.support', module:'recyclerview-v7'
}
```

### 2、In your code

#### Basis Usage

Use `GalleryLayoutManager#attach(RecycleView recycleView)` to setup `GalleryLayoutManager` for your `RecycleView` instead of `RecycleView#setLayoutManager(LayoutManager manager)`

```java
GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
//layoutManager.attach(mPagerRecycleView);  default selected position is 0
layoutManager.attach(mPagerRecycleView, 30);

//...
//setup adapter for your RecycleView
mPagerRecycleView.setAdapter(imageAdapter);
```

#### Listen to selection change

```java
layoutManager2.setCallbackInFling(true);//should receive callback when flinging, default is false
layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
    @Override
    public void onItemSelected(RecyclerView recyclerView, View item, int position) {
        //.....
    }
});
```

#### Apply ItemTransformer just like ViewPager

Implements your `ItemTransformer`

```java
public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2.f);
        item.setPivotY(item.getHeight()/2.0f);
        float scale = 1 - 0.3f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}
```

```java
// Apply ItemTransformer just like ViewPager
layoutManager.setItemTransformer(new ScaleTransformer());
```

## License

```
Copyright [2017] [Hello Csl]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```
