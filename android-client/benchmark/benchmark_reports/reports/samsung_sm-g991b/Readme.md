# Jetpack Compose performance test summary

## Device

**Model:** Samsung SM-G991B

**Android API:** 31

**CPU cores:** 8

**Core clock:** 2.9Gz

**RAM:** 7.5 GB

<br/>

![Samsung SM-G991B](compose_dynamics.svg)

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5
--- | ---: | ---: | ---:
lazyListScroll | `5.1` / `6.3` | `4.8` / `6.7` | `5.2` / `9.9`
particlesCustomLayout | `6.1` / `6.9` | `5.6` / `6.5` | `6.3` / `7.5`
particlesLayoutOffset | `7.8` / `10.9` | `8.1` / `10.1` | `9.4` / `11.0`
navigateToScreen | `4.7` / `9.6` | `4.5` / `6.7` | `4.8` / `7.6`
itemRecomposition | `7.0` / `15.5` | `7.2` / `15.4` | `7.1` / `15.2`
particlesCanvas | `5.4` / `6.1` | `5.5` / `6.3` | `5.6` / `6.3`
lazyListFling | `4.3` / `6.6` | `4.4` / `6.7` | `4.3` / `6.6`
addItemsToColumn | `8.7` / `12.8` | `5.2` / `12.0` | `5.9` / `12.1`
particlesLayoutLayer | `8.5` / `13.8` | `7.4` / `8.7` | `8.5` / `10.4`
transitionAnimation | `10.7` / `13.3` | `10.7` / `13.4` | `10.0` / `13.3`
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

### lazyListScroll
![compose_1.0.3_lazyListScroll_benchmark.svg](benchmark/compose_1.0.3_lazyListScroll_benchmark.svg)
![compose_1.0.4_lazyListScroll_benchmark.svg](benchmark/compose_1.0.4_lazyListScroll_benchmark.svg)
![compose_1.0.5_lazyListScroll_benchmark.svg](benchmark/compose_1.0.5_lazyListScroll_benchmark.svg)

---
### lazyListFling
![compose_1.0.3_lazyListFling_benchmark.svg](benchmark/compose_1.0.3_lazyListFling_benchmark.svg)
![compose_1.0.4_lazyListFling_benchmark.svg](benchmark/compose_1.0.4_lazyListFling_benchmark.svg)
![compose_1.0.5_lazyListFling_benchmark.svg](benchmark/compose_1.0.5_lazyListFling_benchmark.svg)

---
### transitionAnimation
![compose_1.0.3_transitionAnimation_benchmark.svg](benchmark/compose_1.0.3_transitionAnimation_benchmark.svg)
![compose_1.0.4_transitionAnimation_benchmark.svg](benchmark/compose_1.0.4_transitionAnimation_benchmark.svg)
![compose_1.0.5_transitionAnimation_benchmark.svg](benchmark/compose_1.0.5_transitionAnimation_benchmark.svg)

---
### itemRecomposition
![compose_1.0.3_itemRecomposition_benchmark.svg](benchmark/compose_1.0.3_itemRecomposition_benchmark.svg)
![compose_1.0.4_itemRecomposition_benchmark.svg](benchmark/compose_1.0.4_itemRecomposition_benchmark.svg)
![compose_1.0.5_itemRecomposition_benchmark.svg](benchmark/compose_1.0.5_itemRecomposition_benchmark.svg)

---
### addItemsToColumn
![compose_1.0.3_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.3_addItemsToColumn_benchmark.svg)
![compose_1.0.4_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.4_addItemsToColumn_benchmark.svg)
![compose_1.0.5_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.5_addItemsToColumn_benchmark.svg)

---
### navigateToScreen
![compose_1.0.3_navigateToScreen_benchmark.svg](benchmark/compose_1.0.3_navigateToScreen_benchmark.svg)
![compose_1.0.4_navigateToScreen_benchmark.svg](benchmark/compose_1.0.4_navigateToScreen_benchmark.svg)
![compose_1.0.5_navigateToScreen_benchmark.svg](benchmark/compose_1.0.5_navigateToScreen_benchmark.svg)

---
### 500particlesAnimation
![compose_1.0.3_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.3_500particlesAnimation_benchmark.svg)
![compose_1.0.4_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.4_500particlesAnimation_benchmark.svg)
![compose_1.0.5_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.5_500particlesAnimation_benchmark.svg)

---
