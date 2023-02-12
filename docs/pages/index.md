---
layout: page
title: AwesomeAds Android SDK
permalink: /
---

# {{ site.title }}

The {{ site.title }} (Software Development Kit) lets you to easily add COPPA compliant advertisements to your apps.

| Info    | Contents  |
|---------|-----------|
| Version   |   ![Version](https://img.shields.io/github/v/tag/SuperAwesomeLTD/sa-mobile-sdk-android) ([Changelog]({{ site.changelog_url }}))   |
| Support   |   Android 4.1+ (API 16) |
| GitHub    |   [{{ site.repo }}]({{ site.repo }})         |
| Contact   |   [{{ site.email }}]({{ site.email }})        |

Here you can quickly jump to a particular page.

<div class="section-index">
    <hr class="panel-line">
    {% for post in site.docs  %}        
    <div class="entry">
    <h5><a href="{{ post.url | prepend: site.baseurl }}">{{ post.title }}</a></h5>
    <p>{{ post.description }}</p>
    </div>{% endfor %}
</div>

