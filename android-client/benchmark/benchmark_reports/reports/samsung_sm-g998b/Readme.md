# Jetpack Compose performance test summary

## Device

**Model:** Samsung SM-G998B

**Android API:** 31

**CPU cores:** 8

**Core clock:** 2.9Gz

**RAM:** 11.1 GB

<br/>

![Samsung SM-G998B](compose_dynamics.svg)

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5 | 1.1.0 | 1.2.0-rc03
--- | ---: | ---: | ---: | ---: | ---:
lazyListScroll | `5.0` / `6.2` | `5.0` / `6.4` | `5.1` / `9.7` | `2.2` / `2.8` | `5.7` / `9.5`
particlesCustomLayout | `6.5` / `10.3` | `6.0` / `6.9` | `6.1` / `8.1` | `2.8` / `3.1` | `6.2` / `7.2`
particlesLayoutOffset | `6.8` / `7.7` | `8.6` / `10.6` | `8.9` / `10.7` | `5.6` / `5.9` | `14.5` / `16.1`
navigateToScreen | `4.5` / `10.3` | `5.0` / `10.9` | `4.7` / `11.2` | `2.1` / `3.8` | `5.1` / `11.2`
itemRecomposition | `6.4` / `15.5` | `6.6` / `15.6` | `6.0` / `15.2` | `3.1` / `8.2` | `6.9` / `17.4`
particlesCanvas | `5.5` / `6.4` | `5.4` / `6.3` | `5.5` / `6.4` | `2.5` / `2.9` | `5.8` / `6.7`
lazyListFling | `4.7` / `7.0` | `4.7` / `6.2` | `4.7` / `7.0` | `3.5` / `4.6` | `4.9` / `6.6`
addItemsToColumn | `6.4` / `12.6` | `5.0` / `12.4` | `5.6` / `12.2` | `2.3` / `5.7` | `6.4` / `13.2`
particlesLayoutLayer | `7.3` / `8.6` | `9.6` / `11.0` | `7.9` / `9.0` | `5.2` / `5.5` | `10.1` / `11.4`
transitionAnimation | `11.3` / `13.7` | `10.1` / `12.0` | `6.2` / `8.6` | `3.6` / `7.3` | `5.5` / `8.8`
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

### 500particlesAnimation
![compose_1.0.3_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.3_500particlesAnimation_benchmark.svg)
![compose_1.0.4_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.4_500particlesAnimation_benchmark.svg)
![compose_1.0.5_500particlesAnimation_benchmark.svg](benchmark/compose_1.0.5_500particlesAnimation_benchmark.svg)
![compose_1.1.0_500particlesAnimation_benchmark.svg](benchmark/compose_1.1.0_500particlesAnimation_benchmark.svg)
![compose_1.2.0-rc03_500particlesAnimation_benchmark.svg](benchmark/compose_1.2.0-rc03_500particlesAnimation_benchmark.svg)

---
### lazyListScroll
![compose_1.0.3_lazyListScroll_benchmark.svg](benchmark/compose_1.0.3_lazyListScroll_benchmark.svg)
![compose_1.0.4_lazyListScroll_benchmark.svg](benchmark/compose_1.0.4_lazyListScroll_benchmark.svg)
![compose_1.0.5_lazyListScroll_benchmark.svg](benchmark/compose_1.0.5_lazyListScroll_benchmark.svg)
![compose_1.1.0_lazyListScroll_benchmark.svg](benchmark/compose_1.1.0_lazyListScroll_benchmark.svg)
![compose_1.2.0-rc03_lazyListScroll_benchmark.svg](benchmark/compose_1.2.0-rc03_lazyListScroll_benchmark.svg)

---
### lazyListFling
![compose_1.0.3_lazyListFling_benchmark.svg](benchmark/compose_1.0.3_lazyListFling_benchmark.svg)
![compose_1.0.4_lazyListFling_benchmark.svg](benchmark/compose_1.0.4_lazyListFling_benchmark.svg)
![compose_1.0.5_lazyListFling_benchmark.svg](benchmark/compose_1.0.5_lazyListFling_benchmark.svg)
![compose_1.1.0_lazyListFling_benchmark.svg](benchmark/compose_1.1.0_lazyListFling_benchmark.svg)
![compose_1.2.0-rc03_lazyListFling_benchmark.svg](benchmark/compose_1.2.0-rc03_lazyListFling_benchmark.svg)

---
### transitionAnimation
![compose_1.0.3_transitionAnimation_benchmark.svg](benchmark/compose_1.0.3_transitionAnimation_benchmark.svg)
![compose_1.0.4_transitionAnimation_benchmark.svg](benchmark/compose_1.0.4_transitionAnimation_benchmark.svg)
![compose_1.0.5_transitionAnimation_benchmark.svg](benchmark/compose_1.0.5_transitionAnimation_benchmark.svg)
![compose_1.1.0_transitionAnimation_benchmark.svg](benchmark/compose_1.1.0_transitionAnimation_benchmark.svg)
![compose_1.2.0-rc03_transitionAnimation_benchmark.svg](benchmark/compose_1.2.0-rc03_transitionAnimation_benchmark.svg)

---
### itemRecomposition
![compose_1.0.3_itemRecomposition_benchmark.svg](benchmark/compose_1.0.3_itemRecomposition_benchmark.svg)
![compose_1.0.4_itemRecomposition_benchmark.svg](benchmark/compose_1.0.4_itemRecomposition_benchmark.svg)
![compose_1.0.5_itemRecomposition_benchmark.svg](benchmark/compose_1.0.5_itemRecomposition_benchmark.svg)
![compose_1.1.0_itemRecomposition_benchmark.svg](benchmark/compose_1.1.0_itemRecomposition_benchmark.svg)
![compose_1.2.0-rc03_itemRecomposition_benchmark.svg](benchmark/compose_1.2.0-rc03_itemRecomposition_benchmark.svg)

---
### addItemsToColumn
![compose_1.0.3_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.3_addItemsToColumn_benchmark.svg)
![compose_1.0.4_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.4_addItemsToColumn_benchmark.svg)
![compose_1.0.5_addItemsToColumn_benchmark.svg](benchmark/compose_1.0.5_addItemsToColumn_benchmark.svg)
![compose_1.1.0_addItemsToColumn_benchmark.svg](benchmark/compose_1.1.0_addItemsToColumn_benchmark.svg)
![compose_1.2.0-rc03_addItemsToColumn_benchmark.svg](benchmark/compose_1.2.0-rc03_addItemsToColumn_benchmark.svg)

---
### navigateToScreen
![compose_1.0.3_navigateToScreen_benchmark.svg](benchmark/compose_1.0.3_navigateToScreen_benchmark.svg)
![compose_1.0.4_navigateToScreen_benchmark.svg](benchmark/compose_1.0.4_navigateToScreen_benchmark.svg)
![compose_1.0.5_navigateToScreen_benchmark.svg](benchmark/compose_1.0.5_navigateToScreen_benchmark.svg)
![compose_1.1.0_navigateToScreen_benchmark.svg](benchmark/compose_1.1.0_navigateToScreen_benchmark.svg)
![compose_1.2.0-rc03_navigateToScreen_benchmark.svg](benchmark/compose_1.2.0-rc03_navigateToScreen_benchmark.svg)

---
