import sys
import os
extensions = []
templates_path = ['_templates']
source_suffix = '.rst'
master_doc = 'index'
project = u'Android SDK'
copyright = u'2016, SuperAwesome Ltd'
author = u'Gabriel Coman'
version = u'3.6.5'
release = u'3.6.5'
language = None
exclude_patterns = []
show_authors = True
pygments_style = 'sphinx'
todo_include_todos = False
html_theme = 'sphinx_rtd_theme'
html_theme_options = {"logo_only":True}
html_theme_path = ["themes",]
html_logo = 'themeres/logo.png'
html_static_path = ['_static']
htmlhelp_basename = 'SAAndroidSDKdoc'
latex_elements = {}
latex_documents = [
    (master_doc, 'SAAndroidSDK.tex', u'SAAndroidSDK Documentation', u'Gabriel Coman', 'manual'),
]
man_pages = [
    (master_doc, 'saandroidsdk', u'SAAndroidSDK Documentation', [author], 1)
]
texinfo_documents = [
    (master_doc, 'SAAndroidSDK', u'SAAndroidSDK Documentation', author, 'SAAndroidSDK', 'One line description of project.', 'Miscellaneous'),
]
html_context = {
    'all_versions' : [u'3.6.5'],
    'domain': 'AwesomeAds',
    'sourcecode': 'https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android/tree/develop_v3'
}
