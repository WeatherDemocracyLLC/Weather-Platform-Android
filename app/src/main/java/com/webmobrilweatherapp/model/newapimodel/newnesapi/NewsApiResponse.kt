
package com.webmobrilweatherapp.model.newapimodel.newnesapi

import com.google.gson.JsonElement

data class NewsResponse(
    val news: List<NewsItem?>? = null
)

data class NewsItem(

    val id: String? = null,
    val assetName: String? = null,
    val type: String? = null,
    val locale: String? = null,
    val schemaVersion: String? = null,
    val title: String? = null,
    val teaserTitle: String? = null,
    val mobileHeadline: String? = null,
    val author: List<String>? = null,
    val authorBioLink: List<String>? = null,
    val pcollid: String? = null,
    val url: String? = null,
    val description: String? = null,
    val createdate: String? = null,
    val publishdate: String? = null,
    val lastmodifieddate: String? = null,
    val adsmetrics: Adsmetrics? = null,
    val providerid: String? = null,
    val providername: String? = null,
    val distro: Boolean? = null,
    val premium: Boolean? = null,
    val seometa: Seometa? = null
)
data class Adsmetrics(
    val adconfigid: String? = null,
    val adzone: String? = null,
    val pagecode: String? = null
)

data class Seometa(
    val title: String? = null,
    val keywords: String? = null,
    val description: String? = null,
    val og_image: String? = null,
    val og_description: String? = null,
    val canonical: JsonElement? = null
)

fun main() {
    val json = """
    {
        "id": "123",
        "assetName": "news_asset",
        "type": "article",
        "locale": "en_US",
        "schema_version": "1.0",
        "title": "Breaking News",
        "teaserTitle": "Teaser",
        "mobileHeadline": "Mobile Headline",
        "author": ["Author1", "Author2"],
        "author_bio_link": ["link1", "link2"],
        "pcollid": "pc123",
        "url": "https://example.com/news",
        "description": "News description",
        "createdate": "2023-01-01T00:00:00Z",
        "publishdate": "2023-01-01T00:00:00Z",
        "lastmodifieddate": "2023-01-01T00:00:00Z",
        "adsmetrics": {
            "adconfigid": "ad123",
            "adzone": "zone123",
            "pagecode": "page123"
        },
        "providerid": "provider123",
        "providername": "Provider Name",
        "distro": true,
        "premium": false,
        "seometa": {
            "title": "SEO Title",
            "keywords": "keyword1, keyword2",
            "description": "SEO Description",
            "og:image": "https://example.com/image.jpg",
            "og:description": "OG Description",
            "canonical": "https://example.com"
        }
    }
    """
}

