# Jetpack Compose performance test summary

## Device

**Model:** Xiaomi MI 6

**Android API:** 28

**CPU cores:** 8

**Core clock:** 2.5Gz

**RAM:** 6.0 GB

<br/>

![Xiaomi MI 6](compose_dynamics.svg)

# Comparison table
Test name / Compose version (P50/P90, values in ms)

Test | 1.0.3 | 1.0.4 | 1.0.5 | 1.1.0-beta01 | 1.1.0-beta02 | 1.1.0-beta03 | 1.1.0-beta04
--- | ---: | ---: | ---: | ---: | ---: | ---: | ---:
lazyListScroll | `13.0` / `20.2` | `13.1` / `21.9` | `13.4` / `21.6` | `13.7` / `23.9` | `15.1` / `29.0` | `13.5` / `24.0` | `14.2` / `27.6`
particlesCustomLayout | `14.6` / `23.6` | `15.8` / `24.7` | `13.8` / `23.5` | `12.6` / `21.9` | `14.7` / `23.7` | `13.2` / `23.9` | `12.2` / `20.9`
particlesLayoutOffset | `15.8` / `26.6` | `17.6` / `21.9` | `18.9` / `22.6` | `18.7` / `22.5` | `18.3` / `23.2` | `17.5` / `21.6` | `17.6` / `22.0`
navigateToScreen | `10.0` / `22.6` | `11.1` / `18.8` | `11.0` / `21.1` | `11.9` / `20.1` | `11.5` / `25.0` | `11.4` / `23.8` | `11.5` / `27.4`
itemRecomposition | `7.6` / `24.7` | `7.6` / `28.0` | `7.7` / `27.5` | `7.7` / `25.5` | `7.5` / `26.0` | `7.5` / `27.7` | `7.8` / `24.8`
particlesCanvas | `13.0` / `21.3` | `13.9` / `21.5` | `13.3` / `21.2` | `13.3` / `22.1` | `13.3` / `21.5` | `13.3` / `21.6` | `13.0` / `21.2`
lazyListFling | `14.0` / `22.8` | `13.7` / `23.5` | `13.6` / `22.8` | `14.0` / `25.4` | `14.5` / `27.1` | `13.9` / `25.3` | `14.1` / `25.8`
addItemsToColumn | `9.6` / `26.2` | `9.7` / `26.6` | `9.6` / `24.4` | `9.0` / `25.0` | `9.5` / `25.9` | `9.3` / `24.5` | `9.7` / `26.3`
particlesLayoutLayer | `16.6` / `23.3` | `18.6` / `22.6` | `18.7` / `23.4` | `18.6` / `22.6` | `18.5` / `22.6` | `18.4` / `22.2` | `18.4` / `22.2`
transitionAnimation | `17.7` / `21.2` | `19.7` / `25.9` | `22.0` / `26.2` | `17.5` / `21.8` | `21.1` / `26.8` | `18.1` / `23.6` | `19.7` / `26.1`
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
