import { defineConfig } from 'vitepress';
import { withMermaid } from "vitepress-plugin-mermaid";

// https://vitepress.dev/reference/site-config
const config = defineConfig({
  title: "PiperKt",
  description: "PiperKt report",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Report', link: '/01-introduction' },
    ],

    sidebar: [
      {
        items: [
          { text: 'Introduction', link: '/01-introduction', },
          { text: 'Requirements', link: '/02-requirements', },
        ]
      },
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zucchero-sintattico/piper-kt' }
    ]
  }
})

export default withMermaid(config);
