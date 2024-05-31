import { defineConfig } from 'vitepress';
import { withMermaid } from "vitepress-plugin-mermaid";

// https://vitepress.dev/reference/site-config
const config = defineConfig({
  title: "PiperKt",
  description: "PiperKt report",
  ignoreDeadLinks: [
    // ignore all localhost links
    /^https?:\/\/localhost/,
  ],
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
          { text: 'Introduction', link: '/01-introduction/01-01-introduction', },
          { text: 'Knowledge Crunching', link: '/01-introduction/01-02-knowledge-crunching', },
        ]
      },
      {
        items: [
          {
            text: 'Requirements', collapsed: true,
            items: [
              { text: 'Business requirements', link: '/02-requirements/02-01-business', },
              { text: 'Functional requirements', link: '/02-requirements/02-02-functional', },
              { text: 'Non functional requirements', link: '/02-requirements/02-03-non-functional', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'Design', collapsed: true,
            items: [
              { text: 'Architecture', link: '/03-design/03-architecture', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'Implementation', link: "/04-implementation/04-00-index", collapsed: true,
            items: [
              { text: 'Microservices', link: '/04-implementation/04-01-implementation', },
              { text: 'Testing', link: '/04-implementation/04-02-testing', },
              { text: 'Multiplatform', link: '/04-implementation/04-03-multiplatform', },
              { text: 'Experiments', link: '/04-implementation/04-04-experiments', },
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
          { text: 'Deployment', link: '/06-deployment/06-deploy', },
          { text: 'Benchmark', link: '/07-benchmarking/07-01-benchmark', },
        ]
      },
      {
        items: [
          { text: 'Conclusions', link: '/08-conclusions/08-00-conclusions', },
        ]
      },
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zucchero-sintattico/piper-kt' }
    ],
  }
})

export default withMermaid(config);
