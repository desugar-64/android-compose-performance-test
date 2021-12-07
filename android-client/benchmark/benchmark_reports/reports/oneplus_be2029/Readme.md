# Jetpack Compose performance test summary

## Device

**Model:** OnePlus BE2029

**Android API:** 30

**CPU cores:** 8

**Core clock:** 2.1Gz

**RAM:** 5.7 GB

<br/>

![OnePlus BE2029](compose_dynamics.svg)

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5 | 1.1.0-beta01 | 1.1.0-beta02 | 1.1.0-beta03 | 1.1.0-beta04
--- | ---: | ---: | ---: | ---: | ---: | ---: | ---:
lazyListScroll | `5.2` / `6.3` | `5.3` / `6.5` | `5.3` / `6.4` | `5.3` / `6.4` | `5.1` / `6.3` | `5.4` / `6.2` | `5.2` / `6.3`
particlesCustomLayout | `5.7` / `6.1` | `6.0` / `6.4` | `5.9` / `6.3` | `5.8` / `6.3` | `6.1` / `6.6` | `6.0` / `6.4` | `5.7` / `6.2`
particlesLayoutOffset | `6.5` / `7.0` | `11.0` / `11.6` | `11.0` / `11.6` | `10.9` / `11.6` | `10.8` / `11.4` | `11.2` / `11.9` | `10.6` / `11.2`
navigateToScreen | `4.7` / `7.6` | `4.9` / `7.9` | `4.9` / `7.8` | `4.9` / `7.4` | `4.8` / `7.5` | `4.9` / `7.3` | `4.9` / `7.3`
itemRecomposition | `5.2` / `9.8` | `5.1` / `10.2` | `5.3` / `10.0` | `5.4` / `10.0` | `5.1` / `9.8` | `5.0` / `10.0` | `5.4` / `10.4`
particlesCanvas | `5.1` / `5.6` | `5.1` / `5.6` | `5.1` / `5.6` | `5.2` / `5.6` | `5.1` / `5.5` | `5.2` / `5.7` | `5.1` / `5.6`
lazyListFling | `5.4` / `7.5` | `5.5` / `7.5` | `5.5` / `7.7` | `5.5` / `7.5` | `5.6` / `7.6` | `5.6` / `7.6` | `5.6` / `7.9`
addItemsToColumn | `5.6` / `10.6` | `5.4` / `11.7` | `5.4` / `11.3` | `5.4` / `11.3` | `5.3` / `11.2` | `5.3` / `11.2` | `5.3` / `11.8`
particlesLayoutLayer | `8.2` / `8.8` | `10.0` / `12.1` | `9.7` / `10.2` | `9.8` / `10.4` | `9.9` / `10.4` | `10.1` / `10.6` | `9.7` / `10.3`
transitionAnimation | `8.7` / `9.7` | `8.0` / `9.0` | `7.7` / `8.6` | `14.5` / `20.2` | `12.6` / `14.3` | `12.0` / `16.9` | `10.8` / `13.2`
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
