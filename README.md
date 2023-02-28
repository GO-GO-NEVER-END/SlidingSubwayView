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
- ~~완전히 센터에 위치를 고정시키고 싶은데... 아직 좀 덜컹덜컹거리는중~~

![yeah](https://user-images.githubusercontent.com/69582122/221772586-a00f7fe3-fa44-4828-a139-c7c18a21a567.gif)
- SlidingSubwayView가 선택된 Station을 알도록 수정
- 드래그가 끝난 시점에서 선택된 Station의 focus 애니메이션 실행
- RecyclerView의 속도를 늦춰 휙휙 넘어가지 않고 부드럽게 넘어가도록 조정
- 선택된 역이 중간에 오도록 함. (근데 기준이 findFirstVisibleItemPosition 이어서 왼쪽 기준으로 맞춰지는 느낌쓰가 없잖아 있음)

### Reference

[Creating Your Own Animated Custom View in Android](http://raphaelfavero.github.io/Creating_Animated_Custom_View/)
