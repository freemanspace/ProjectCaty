# ProjectCaty
國泰金控考題

1.使用MVVM架構開發，因為不喜歡部分邏輯在layout的xml控制，所以不使用databinding，使用viewbinding
2.動物園各館資訊使用room儲存，保留一日，植物資訊即時取得每次20筆
3.非同步的動作使用Coroutine控制，不控制Thread
4.習慣自己控制fragment的控制，所以沒使用navigation
5.自己封裝ApiManager(使用livedata & okhttp ＆ Coroutine)來處理api的呼叫，沒有使用retrofit
