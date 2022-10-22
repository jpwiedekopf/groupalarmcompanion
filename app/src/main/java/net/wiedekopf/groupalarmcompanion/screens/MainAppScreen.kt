package net.wiedekopf.groupalarmcompanion.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.wiedekopf.groupalarmcompanion.R
import net.wiedekopf.groupalarmcompanion.shared.client.GroupAlarmClient
import net.wiedekopf.groupalarmcompanion.shared.model.Organization
import net.wiedekopf.groupalarmcompanion.shared.model.OrganizationList
import net.wiedekopf.groupalarmcompanion.shared.model.UserDetails

const val TAG = "MainAppScreen"

@Composable
fun MainAppScreen(client: GroupAlarmClient) {
    val organizationList by remember {
        mutableStateOf(client.getOrganizationList())
    }
    val userDetails by remember {
        mutableStateOf(client.getUser())
    }
    UserCard(userDetails)
    OrganizationCard(organizationList)
}

@Composable
fun CardWithAvatar(
    avatar: @Composable () -> Unit,
    nextToAvatarContent: @Composable () -> Unit,
    bottomContent: (@Composable () -> Unit)?
) {
    MyCard {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            avatar()
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                nextToAvatarContent()
            }
        }
        bottomContent?.invoke()
    }
}

@Composable
fun MyCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            content()
        }
    }
}

@Composable
fun OrganizationCard(organizationList: OrganizationList) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(organizationList.organizations, key = Organization::id) { org ->
            when (org.avatarURL) {
                null -> MyCard {
                    Text(
                        text = stringResource(id = R.string.organization_name_format).format(org.name),
                        style = typography.headlineSmall
                    )
                }
                else -> CardWithAvatar(avatar = {
                    Avatar(
                        url = org.avatarURL.toString(),
                        contentDescription = R.string.organization_avatar,
                        placeholder = R.drawable.corporate_fare_48px
                    )
                }, nextToAvatarContent = {
                    Text(
                        text = stringResource(id = R.string.organization_name_format).format(org.name),
                        style = typography.headlineSmall
                    )
                }, bottomContent = null)
            }
        }
    }
}

@Composable
private fun Avatar(
    url: String,
    @DrawableRes placeholder: Int? = null,
    @StringRes contentDescription: Int,
    avatarSize: Dp = 64.dp,
) {
    AsyncImage(
        onLoading = {
            Log.d(TAG, "loading from $url")
        },
        onSuccess = {
            Log.i(TAG, "success loading image $url")
        },
        onError = {
            Log.e(TAG, "error loading image: ${it.result.throwable}")
        },
        model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build(),
        placeholder = placeholder?.let { painterResource(id = it) },
        modifier = Modifier
            .clip(CircleShape)
            .size(avatarSize),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = contentDescription)
    )
}

@Composable
fun UserCard(userDetails: UserDetails) = when (userDetails.avatarUrl) {
    null -> MyCard {
        Text(
            text = userDetails.nameFormat, style = typography.headlineSmall
        )
        Text(text = userDetails.email)
    }
    else -> CardWithAvatar(avatar = {
        Avatar(
            url = userDetails.avatarUrl.toString(),
            placeholder = R.drawable.engineering_48px,
            contentDescription = R.string.user_avatar,
            avatarSize = 96.dp
        )
    }, nextToAvatarContent = {
        Text(
            text = userDetails.nameFormat, style = typography.headlineSmall
        )
    }) {
        Text(text = userDetails.email, modifier = Modifier.padding(bottom = 4.dp))
    }
}
