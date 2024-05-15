package com.example.buildProduct.movies.data

data class TitleType(
	val isEpisode: Boolean? = null,
	val typename: String? = null,
	val text: String? = null,
	val id: String? = null,
	val isSeries: Boolean? = null
)

data class Caption(
	val typename: String? = null,
	val plainText: String? = null
)

data class OriginalTitleText(
	val typename: String? = null,
	val text: String? = null
)

data class ReleaseDate(
	val month: String? = null,
	val year: String? = null,
	val typename: String? = null,
	val day: String? = null
)

data class ResultsItem(
	val titleType: TitleType? = null,
	val primaryImage: PrimaryImage? = null,
	val releaseDate: Any? = null,
	val originalTitleText: OriginalTitleText? = null,
	val titleText: TitleText? = null,
	val id: String? = null,
	val releaseYear: ReleaseYear? = null
)

data class PrimaryImage(
	val typename: String? = null,
	val width: Int? = null,
	val caption: Caption? = null,
	val id: String? = null,
	val url: String? = null,
	val height: Int? = null
)

data class ReleaseYear(
	val year: Int? = null,
	val typename: String? = null,
	val endYear: Any? = null
)

data class TitleText(
	val typename: String? = null,
	val text: String? = null
)

data class Plot(
	val plotText: PlotText? = null
)

data class PlotText(
	val plainText: String? = null
)

data class Genre(
	val text: String? = null
)
data class Genres(
	val genres: List<Genre>
)

