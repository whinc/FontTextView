### FontTextView

FontTextView extends from TextView, you can change the font by specify the font fontPath relative to `assets` directory.

![screenshot](./screenshot.png)

### Integration（集成）

This library has been published on [jcenter][1], you can use it like below:

```
dependencies {
    ...
    compile 'com.whinc.widget.fontview:fontview:0.1.0'
}
```

### How to use （如何使用）

Use in xml layout file:

```
<com.whinc.widget.fontview.FontTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/hello_world"
    app:fontPath="fonts/SnellRoundhand.ttf"
    />
```

Use in java code:

```
FontTextView fontTextView = new FontTextView(this);
fontTextView.setPath("fonts/SnellRoundhand.ttf");
fontTextView.setText("Hello world!");
```

### How to customise （自定义）

First, make sure you have add below namespace to the layout root tag.

```
xmlns:app="http://schemas.android.com/apk/res-auto"
```

Here is all the attributes you can use to customise FontTextView

* app:fontPath [String default:null] --> font file data fontPath in `assets` directory


### The MIT License (MIT)

Copyright (c) 2015 WuHui

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

[1]:https://bintray.com/whinc/maven/fontview/view
