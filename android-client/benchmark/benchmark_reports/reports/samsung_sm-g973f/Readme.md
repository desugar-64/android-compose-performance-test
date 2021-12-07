# Jetpack Compose performance test summary

## Device

**Model:** Samsung SM-G973F

**Android API:** 30

**CPU cores:** 8

**Core clock:** 2.7Gz

**RAM:** 7.8 GB

<br/>

![Samsung SM-G973F](compose_dynamics.svg)

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5 | 1.1.0-beta01 | 1.1.0-beta02 | 1.1.0-beta03 | 1.1.0-beta04
--- | ---: | ---: | ---: | ---: | ---: | ---: | ---:
lazyListScroll | `9.0` / `12.8` | `10.0` / `14.8` | `9.1` / `13.1` | `9.4` / `13.4` | `10.1` / `14.5` | `10.1` / `14.0` | `9.8` / `14.2`
particlesCustomLayout | `13.9` / `17.2` | `13.6` / `16.7` | `13.3` / `16.3` | `13.7` / `16.6` | `13.5` / `16.3` | `13.5` / `16.5` | `12.7` / `15.3`
particlesLayoutOffset | `15.2` / `18.3` | `20.9` / `27.7` | `22.3` / `27.8` | `20.4` / `26.8` | `20.7` / `27.4` | `21.2` / `27.7` | `23.5` / `27.9`
navigateToScreen | `10.0` / `28.4` | `8.3` / `20.2` | `8.6` / `21.1` | `8.9` / `26.2` | `8.3` / `11.7` | `9.0` / `27.3` | `8.4` / `22.9`
itemRecomposition | `12.7` / `22.0` | `13.2` / `22.2` | `13.2` / `22.0` | `12.9` / `22.7` | `12.9` / `22.1` | `13.0` / `23.1` | `14.4` / `24.1`
particlesCanvas | `11.0` / `13.3` | `11.1` / `13.5` | `11.2` / `13.2` | `11.0` / `13.1` | `10.8` / `12.8` | `11.1` / `13.2` | `10.6` / `12.5`
lazyListFling | `10.7` / `15.4` | `10.2` / `14.7` | `10.8` / `15.8` | `10.8` / `15.5` | `10.8` / `15.4` | `11.2` / `16.1` | `10.7` / `15.7`
addItemsToColumn | `12.0` / `24.5` | `15.5` / `28.2` | `12.1` / `27.0` | `12.1` / `26.9` | `12.6` / `27.2` | `11.1` / `25.7` | `13.9` / `27.6`
particlesLayoutLayer | `19.0` / `24.5` | `21.3` / `28.0` | `24.7` / `28.2` | `24.4` / `28.0` | `20.4` / `27.3` | `24.9` / `28.5` | `20.7` / `27.5`
transitionAnimation | `27.0` / `31.5` | `28.2` / `32.3` | `27.2` / `31.4` | `26.8` / `31.4` | `27.3` / `30.6` | `25.3` / `30.7` | `25.1` / `30.1`
<br/>

# lazyListScroll

Preview | Description
----- | -----
| ![lazyListScroll](image/lazyListScroll.webp) | This test measures the smoothness of the LazyList scrolling filled with an arbitrary layout type. It contains a lot of text, emojis, images, custom layouts, animations. The test case is as close as possible to the day-to-day applications. Relaxed scrolling of the list. |

![summary](summary/lazyListScroll_summary.svg)

<br/>

# particlesCustomLayout

Preview | Description
----- | -----
| ![particlesCustomLayout](image/particlesCustomLayout.webp) | This test measures the performance of the custom layout system in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using the Layout placing mechanism. |

![summary](summary/particlesCustomLayout_summary.svg)

<br/>

# particlesLayoutOffset

Preview | Description
----- | -----
| ![particlesLayoutOffset](image/particlesLayoutOffset.webp) | This test measures performance of animating layout positions using the `offset` modifier in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using `Modifier.offset { ... }`. |

![summary](summary/particlesLayoutOffset_summary.svg)

<br/>

# navigateToScreen

Preview | Description
----- | -----
| ![navigateToScreen](image/navigateToScreen.webp) | This test measures the smoothness of a standard transition between views using AnimatedNavHost. Taps on an item and routes to the details screen. |

![summary](summary/navigateToScreen_summary.svg)

<br/>

# itemRecomposition

Preview | Description
----- | -----
| ![itemRecomposition](image/itemRecomposition.webp) | This test measures the recomposition mechanism itself. It quickly replaces items one by one. |

![summary](summary/itemRecomposition_summary.svg)

<br/>

# particlesCanvas

Preview | Description
----- | -----
| ![particlesCanvas](image/particlesCanvas.webp) | This test measures canvas performance in Jetpack Compose. Draws 500 balls bouncing off the walls on the canvas. |

![summary](summary/particlesCanvas_summary.svg)

<br/>

# lazyListFling

Preview | Description
----- | -----
| ![lazyListFling](image/lazyListFling.webp) | This test measures the smoothness of the LazyList scrolling filled with an arbitrary layout type. It contains a lot of text, emojis, images, custom layouts, animations. The test case is as close as possible to the day-to-day applications. Scrolls quickly through the list. |

![summary](summary/lazyListFling_summary.svg)

<br/>

# addItemsToColumn

Preview | Description
----- | -----
| ![addItemsToColumn](image/addItemsToColumn.webp) | This test measures the dynamic addition of items to a column.Ñ Adds 20 custom layouts to the Column with an animation. |

![summary](summary/addItemsToColumn_summary.svg)

<br/>

# particlesLayoutLayer

Preview | Description
----- | -----
| ![particlesLayoutLayer](image/particlesLayoutLayer.webp) | This test measures performance of animating layout positions using the `graphicLayer` modifier in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using `Modifier.graphicLayer { ... }`. |

![summary](summary/particlesLayoutLayer_summary.svg)

<br/>

# transitionAnimation

Preview | Description
----- | -----
| ![transitionAnimation](image/transitionAnimation.webp) | This test measures the smoothness of transition animations on layouts. Triggers transition animations across dozens of layouts on the screen. |

![summary](summary/transitionAnimation_summary.svg)



<br/>

## Benchmarks

### transitionAnimation
![compose_1.0.3_transitionAnimation_benchmark.svg](benchmark/compose_1.0.3_transitionAnimation_benchmark.svg)
![compose_1.0.4_transitionAnimation_benchmark.svg](benchmark/compose_1.0.4_transitionAnimation_benchmark.svg)
![compose_1.0.5_transitionAnimation_benchmark.svg](benchmark/compose_1.0.5_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta01_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta01_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta02_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta02_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta03_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta03_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta04_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta04_transitionAnimation_benchmark.svg)

---
### lazyListScroll
![compose_1.0.3_lazyListScroll_benchmark.svg](benchmark/compose_1.0.3_lazyListScroll_benchmark.svg)
![compose_1.0.4_lazyListScroll_benchmark.svg](benchmark/compose_1.0.4_lazyListScroll_benchmark.svg)
![compose_1.0.5_lazyListScroll_benchmark.svg](benchmark/compose_1.0.5_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta01_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta01_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta02_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta02_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta03_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta03_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta04_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta04_lazyListScroll_benchmark.svg)

---
### 500particlesAnimation
![compose_1.0.3_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.3_500particlesAnimation_benchmark.svg)
![compose_1.0.4_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.4_500particlesAnimation_benchmark.svg)
![compose_1.0.5_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.5_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta01_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta01_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta02_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta02_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta03_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta03_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta04_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta04_500particlesAnimation_benchmark.svg)

---
### lazyListFling
![compose_1.0.3_lazyListFling_benchmark.svg](benchmark/compose_1.0.3_lazyListFling_benchmark.svg)
![compose_1.0.4_lazyListFling_benchmark.svg](benchmark/compose_1.0.4_lazyListFling_benchmark.svg)
![compose_1.0.5_lazyListFling_benchmark.svg](benchmark/compose_1.0.5_lazyListFling_benchmark.svg)
![compose_1.1.0-beta01_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta01_lazyListFling_benchmark.svg)
![compose_1.1.0-beta02_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta02_lazyListFling_benchmark.svg)
![compose_1.1.0-beta03_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta03_lazyListFling_benchmark.svg)
![compose_1.1.0-beta04_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta04_lazyListFling_benchmark.svg)

---
### navigateToScreen
![compose_1.0.3_navigateToScreen_benchmark.svg](benchmark/compose_1.0.3_navigateToScreen_benchmark.svg)
![compose_1.0.4_navigateToScreen_benchmark.svg](benchmark/compose_1.0.4_navigateToScreen_benchmark.svg)
![compose_1.0.5_navigateToScreen_benchmark.svg](benchmark/compose_1.0.5_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta01_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta01_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta02_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta02_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta03_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta03_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta04_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta04_navigateToScreen_benchmark.svg)

---
### itemRecomposition
![compose_1.0.3_itemRecomposition_benchmark.svg](benchmark/compose_1.0.3_itemRecomposition_benchmark.svg)
![compose_1.0.4_itemRecomposition_benchmark.svg](benchmark/compose_1.0.4_itemRecomposition_benchmark.svg)
![compose_1.0.5_itemRecomposition_benchmark.svg](benchmark/compose_1.0.5_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta01_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta01_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta02_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta02_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta03_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta03_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta04_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta04_itemRecomposition_benchmark.svg)

---
### addItemsToColumn
![compose_1.0.3_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.3_addItemsToColumn_benchmark.svg)
![compose_1.0.4_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.4_addItemsToColumn_benchmark.svg)
![compose_1.0.5_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.5_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta01_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta01_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta02_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta02_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta03_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta03_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta04_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta04_addItemsToColumn_benchmark.svg)

---
