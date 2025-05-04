package com.ayaan.kharbarkhotala.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallIconSize
import com.ayaan.kharbarkhotala.ui.theme.Black
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.ui.theme.Blue


@Composable
fun SearchBar(
    modifier: Modifier=Modifier,
    text: String,
    readOnly: Boolean,
    onClick:(()->Unit)?=null,
    onValueChange:(String)->Unit,
    onSearch:()->Unit,
){
    val interactionSource = remember{
        MutableInteractionSource()
    }
    val isClicked=interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1=isClicked) {
        if(isClicked){
            onClick?.invoke()
        }
    }
    Box(modifier= modifier){
        TextField(
            modifier=Modifier.fillMaxWidth().padding(SmallPadding).searchBarBorder(),
            value=text,
            onValueChange = {onValueChange(it)},
            readOnly=readOnly,
            leadingIcon = {
                Icon(
                    painter=painterResource(id=R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint= colorResource(id=R.color.body)
                )
            },
            placeholder = {
                Text(
                    text="Search",
                    style= MaterialTheme.typography.bodySmall,
                    color= colorResource(id=R.color.placeholder)
                )
            },
            shape= MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.input_background),
                unfocusedContainerColor = colorResource(id = R.color.input_background),
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                cursorColor = Blue,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine=true,
            keyboardActions=KeyboardActions(
                onSearch={
                    onSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            textStyle= MaterialTheme.typography.bodySmall,
            interactionSource=interactionSource
        )
    }
}


@Composable
fun Modifier.searchBarBorder()=composed{
        border(
            width=1. dp,
            color= Black,
            shape= MaterialTheme.shapes.medium
        )
}