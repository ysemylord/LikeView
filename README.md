# LikeView
仿即刻点赞效果

## 效果图
![image](https://github.com/ysemylord/LikeView/blob/master/SVID_20171126_182921.mp4)

## 使用方法
xml
```
<com.xyb.like.LikeView
        app:l_number_text_size="12sp"
        app:l_status="not_thumb"
        app:l_number="2"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"></com.xyb.like.LikeView>

```
## 属性  
|**属性名称**|**意义**|**类型**|
|--|--|--|
|l_number_text_size      | 文字大小     | float|
|l_status      | 尺子的最大刻度值     | enum(not_thumb,thumb)|
|l_number | 点赞数量     | int| 

## 扩展 
左边跳动的数字和右边的拇指是分开实现的，所以可以单独使用。


