# Jetpack Compose performance test summary

## Device

**Model:** samsung SM-G973F

**Android API:** 30

**CPU cores:** 8

**Core clock:** 2.7Gz

**RAM:** 7.8 GB

<br/>

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5 | 1.1.0-beta01 | 1.1.0-beta02
--- | ---: | ---: | ---: | ---: | ---:
addItemsToColumn | `7.9` / `17.2` | `12.2` / `27.5` | `13.0` / `27.8` | `13.0` / `27.5` | `12.3` / `26.8`
itemRecomposition | `9.1` / `18.5` | `10.7` / `23.6` | `10.7` / `23.6` | `11.4` / `23.4` | `10.7` / `22.7`
lazyListFling | `10.5` / `14.8` | `10.4` / `14.9` | `10.5` / `15.3` | `10.6` / `14.9` | `10.6` / `15.1`
lazyListScroll | `7.2` / `10.3` | `9.5` / `13.3` | `9.9` / `13.8` | `9.6` / `13.4` | `9.5` / `13.6`
navigateToScreen | `6.3` / `12.5` | `9.3` / `27.0` | `9.6` / `25.7` | `9.7` / `28.1` | `10.5` / `27.8`
particlesCanvas | `11.2` / `13.2` | `11.2` / `13.0` | `11.0` / `13.1` | `11.2` / `13.5` | `11.3` / `13.3`
particlesCustomLayout | `11.4` / `15.1` | `14.4` / `26.1` | `13.9` / `16.9` | `13.9` / `16.8` | `13.5` / `16.5`
particlesLayoutLayer | `17.8` / `25.4` | `21.0` / `27.6` | `20.6` / `25.7` | `22.7` / `28.5` | `22.8` / `28.3`
particlesLayoutOffset | `15.1` / `17.9` | `21.4` / `27.6` | `25.2` / `28.5` | `21.3` / `27.4` | `21.8` / `27.6`
transitionAnimation | `26.5` / `31.6` | `28.7` / `33.0` | `28.0` / `32.1` | `28.2` / `31.7` | `27.6` / `31.2`
<br/>

# addItemsToColumn

Preview | Description
----- | -----
| ![addItemsToColumn](image/addItemsToColumn.webp) | This test measures the dynamic addition of items to a column.Ñ Adds 20 custom layouts to the Column with an animation. |

![summary](summary/addItemsToColumn_summary.svg)

<br/>

# itemRecomposition

Preview | Description
----- | -----
| ![itemRecomposition](image/itemRecomposition.webp) | This test measures the recomposition mechanism itself. It quickly replaces items one by one. |

![summary](summary/itemRecomposition_summary.svg)

<br/>

# lazyListFling

Preview | Description
----- | -----
| ![lazyListFling](image/lazyListFling.webp) | This test measures the smoothness of the LazyList scrolling filled with an arbitrary layout type. It contains a lot of text, emojis, images, custom layouts, animations. The test case is as close as possible to the day-to-day applications. Scrolls quickly through the list. |

![summary](summary/lazyListFling_summary.svg)

<br/>

# lazyListScroll

Preview | Description
----- | -----
| ![lazyListScroll](image/lazyListScroll.webp) | This test measures the smoothness of the LazyList scrolling filled with an arbitrary layout type. It contains a lot of text, emojis, images, custom layouts, animations. The test case is as close as possible to the day-to-day applications. Relaxed scrolling of the list. |

![summary](summary/lazyListScroll_summary.svg)

<br/>

# navigateToScreen

Preview | Description
----- | -----
| ![navigateToScreen](image/navigateToScreen.webp) | This test measures the smoothness of a standard transition between views using AnimatedNavHost. Taps on an item and routes to the details screen. |

![summary](summary/navigateToScreen_summary.svg)

<br/>

# particlesCanvas

Preview | Description
----- | -----
| ![particlesCanvas](image/particlesCanvas.webp) | This test measures canvas performance in Jetpack Compose. Draws 500 balls bouncing off the walls on the canvas. |

![summary](summary/particlesCanvas_summary.svg)

<br/>

# particlesCustomLayout

Preview | Description
----- | -----
| ![particlesCustomLayout](image/particlesCustomLayout.webp) | This test measures the performance of the custom layout system in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using the Layout placing mechanism. |

![summary](summary/particlesCustomLayout_summary.svg)

<br/>

# particlesLayoutLayer

Preview | Description
----- | -----
| ![particlesLayoutLayer](image/particlesLayoutLayer.webp) | This test measures performance of animating layout positions using the `graphicLayer` modifier in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using `Modifier.graphicLayer { ... }`. |

![summary](summary/particlesLayoutLayer_summary.svg)

<br/>

# particlesLayoutOffset

Preview | Description
----- | -----
| ![particlesLayoutOffset](image/particlesLayoutOffset.webp) | This test measures performance of animating layout positions using the `offset` modifier in Jetpack Compose. â ï¸ Visually, it is identical to the canvas test. Adds 500 Box layouts and moves them around, updating their positions using `Modifier.offset { ... }`. |

![summary](summary/particlesLayoutOffset_summary.svg)

<br/>

# transitionAnimation

Preview | Description
----- | -----
| ![transitionAnimation](image/transitionAnimation.webp) | This test measures the smoothness of transition animations on layouts. Triggers transition animations across dozens of layouts on the screen. |

![summary](summary/transitionAnimation_summary.svg)



<br/>

## Benchmarks

### 500particlesAnimation
![compose_1.0.3_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.3_500particlesAnimation_benchmark.svg)
![compose_1.0.4_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.4_500particlesAnimation_benchmark.svg)
![compose_1.0.5_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.5_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta01_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta01_500particlesAnimation_benchmark.svg)
![compose_1.1.0-beta02_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0-beta02_500particlesAnimation_benchmark.svg)

---
### addItemsToColumn
![compose_1.0.3_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.3_addItemsToColumn_benchmark.svg)
![compose_1.0.4_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.4_addItemsToColumn_benchmark.svg)
![compose_1.0.5_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.5_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta01_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta01_addItemsToColumn_benchmark.svg)
![compose_1.1.0-beta02_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0-beta02_addItemsToColumn_benchmark.svg)

---
### itemRecomposition
![compose_1.0.3_itemRecomposition_benchmark.svg](benchmark/compose_1.0.3_itemRecomposition_benchmark.svg)
![compose_1.0.4_itemRecomposition_benchmark.svg](benchmark/compose_1.0.4_itemRecomposition_benchmark.svg)
![compose_1.0.5_itemRecomposition_benchmark.svg](benchmark/compose_1.0.5_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta01_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta01_itemRecomposition_benchmark.svg)
![compose_1.1.0-beta02_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0-beta02_itemRecomposition_benchmark.svg)

---
### lazyListFling
![compose_1.0.3_lazyListFling_benchmark.svg](benchmark/compose_1.0.3_lazyListFling_benchmark.svg)
![compose_1.0.4_lazyListFling_benchmark.svg](benchmark/compose_1.0.4_lazyListFling_benchmark.svg)
![compose_1.0.5_lazyListFling_benchmark.svg](benchmark/compose_1.0.5_lazyListFling_benchmark.svg)
![compose_1.1.0-beta01_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta01_lazyListFling_benchmark.svg)
![compose_1.1.0-beta02_lazyListFling_benchmark.svg](benchmark/compose_1.1.0-beta02_lazyListFling_benchmark.svg)

---
### lazyListScroll
![compose_1.0.3_lazyListScroll_benchmark.svg](benchmark/compose_1.0.3_lazyListScroll_benchmark.svg)
![compose_1.0.4_lazyListScroll_benchmark.svg](benchmark/compose_1.0.4_lazyListScroll_benchmark.svg)
![compose_1.0.5_lazyListScroll_benchmark.svg](benchmark/compose_1.0.5_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta01_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta01_lazyListScroll_benchmark.svg)
![compose_1.1.0-beta02_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0-beta02_lazyListScroll_benchmark.svg)

---
### navigateToScreen
![compose_1.0.3_navigateToScreen_benchmark.svg](benchmark/compose_1.0.3_navigateToScreen_benchmark.svg)
![compose_1.0.4_navigateToScreen_benchmark.svg](benchmark/compose_1.0.4_navigateToScreen_benchmark.svg)
![compose_1.0.5_navigateToScreen_benchmark.svg](benchmark/compose_1.0.5_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta01_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta01_navigateToScreen_benchmark.svg)
![compose_1.1.0-beta02_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0-beta02_navigateToScreen_benchmark.svg)

---
### transitionAnimation
![compose_1.0.3_transitionAnimation_benchmark.svg](benchmark/compose_1.0.3_transitionAnimation_benchmark.svg)
![compose_1.0.4_transitionAnimation_benchmark.svg](benchmark/compose_1.0.4_transitionAnimation_benchmark.svg)
![compose_1.0.5_transitionAnimation_benchmark.svg](benchmark/compose_1.0.5_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta01_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta01_transitionAnimation_benchmark.svg)
![compose_1.1.0-beta02_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0-beta02_transitionAnimation_benchmark.svg)

---
