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

    outline: "deep",

    sidebar: [
      {
        items: [
          { text: 'Introduction', link: '/01-introduction', },
          { text: 'Requirements', link: '/02-requirements', },
          { text: 'Design', link: '/03-design', },
          { text: 'Implementation', link: '/04-implementation', },
        ]
      },
      {
        items: [
          {
            text: 'DevOps', link: "/05-devops/05-00-devops", collapsed: true,
            items: [
              { text: 'Project Structure', link: '/05-devops/05-1-project-structure', },
              { text: 'VCS & Repo', link: '/05-devops/05-2-version-control', },
            ]
          },
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zucchero-sintattico/piper-kt' }
    ]
  }
})

export default withMermaid(config);
