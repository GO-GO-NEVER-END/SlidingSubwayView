# SlidingSubwayView
간지나는 지하철 노선도 뷰

## StationCircleView

![circle](https://user-images.githubusercontent.com/69582122/220157107-96353f5e-69c3-4c02-8f74-d3437f172a2f.gif)

- 지하철 노선의 역을 나타내는 뷰
- Focus / Idle 상태로 존재하며, 상태가 변경될 때 위와 같은 애니메이션이 실행된다

## SlidingSubwayView

![focus](https://user-images.githubusercontent.com/69582122/220326023-e1571c58-fa7d-42ba-988c-f24d8d7a6334.gif)

- 노선도 직선 위에 Stastion RecyclerView를 띄워서 구현
- 가운데에 위치한 역을 포커싱 (지금은 5개라는 가정 하에 처음 보이기 시작한 뷰부터 2번째 위치한 아이템)
- 완전히 센터에 위치를 고정시키고 싶은데... 아직 좀 덜컹덜컹거리는중 


### Reference

[Creating Your Own Animated Custom View in Android](http://raphaelfavero.github.io/Creating_Animated_Custom_View/)
