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
        ]
      },
      {
        items: [
          {
            text: 'Implementation', link: "/04-implementation/04-00-index", collapsed: true,
            items: [
              { text: 'Microservices', link: '/04-implementation/04-01-implementation', },
              { text: 'Multiplatform', link: '/04-implementation/04-02-multiplatform', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'DevOps', link: "/05-devops/05-00-devops", collapsed: true,
            items: [
              { text: 'Project Structure', link: '/05-devops/05-1-project-structure', },
              { text: 'VCS & Repo', link: '/05-devops/05-2-version-control', },
              { text: 'Quality Assurance', link: '/05-devops/05-3-qa', },
              { text: 'CI/CD', link: '/05-devops/05-4-ci', },
            ]
          },
        ]
      },
      {
        items: [
          { text: 'Benchmark', link: '/07-benchmarking/07-01-benchmark', },
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zucchero-sintattico/piper-kt' }
    ]
  }
})

export default withMermaid(config);
