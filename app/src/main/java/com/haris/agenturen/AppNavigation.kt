package com.haris.agenturen

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.haris.agenturen.main.MainViewModel
import com.haris.create.Create
import com.haris.home.Home
import com.haris.login.Login

internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object Login : LeafScreen("login")

    object Create : LeafScreen("create/{id}") {
        fun createRoute(root: Screen, id: Long?): String {
            return "${root.route}/create/$id"
        }
    }
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { defaultEnterTransition(initialState, targetState) },
        exitTransition = { defaultExitTransition(initialState, targetState) },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() },
        modifier = modifier,
    ) {
        addHomeTopLevel(navController)
        addLoginTopLevel {
            viewModel.setLoggedIn(true)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home),
    ) {
        addHome(navController, Screen.Home)
        addCreate(navController, Screen.Home)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addLoginTopLevel(
    onLogin: () -> Unit
) {
    navigation(
        route = Screen.Login.route,
        startDestination = LeafScreen.Login.createRoute(Screen.Login),
    ) {
        addLogin(Screen.Login, onLogin)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHome(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Home.createRoute(root)
    ) {
        Home(navigateToCreate = {
            val value = it ?: -1L
            navController.navigate(LeafScreen.Create.createRoute(root, value))
        })
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addLogin(
    root: Screen,
    onLogin: () -> Unit
) {
    composable(
        route = LeafScreen.Login.createRoute(root)
    ) {
        Login(onLogin)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreate(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Create.createRoute(root),
        arguments = listOf(
            navArgument("id") { type = NavType.LongType },
        ),
    ) {
        Create(navigateUp = navController::navigateUp)
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}
