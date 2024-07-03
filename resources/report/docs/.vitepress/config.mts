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
  head: [['link', { rel: 'icon', href: '/favicon.ico' }]],
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Report', link: '/01-introduction/01-01-introduction' },
    ],

    outline: "deep",

    sidebar: [
      {
        items: [
          { text: 'Introduction', link: '/01-introduction/01-01-introduction', },
          { text: 'Deliverables', link: '/deliverables/d1-glossary', },
        ]
      },
      {
        items: [
          {
            text: 'Analysis', collapsed: false,
            items: [
              { text: 'Business requirements', link: '/02-analysis/02-01-business', },
              { text: 'Functional requirements', link: '/02-analysis/02-02-functional', },
              { text: 'Non functional requirements', link: '/02-analysis/02-03-non-functional', },
              { text: 'User Stories', link: '/02-analysis/02-04-user-stories', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'Design', collapsed: false,
            items: [
              { text: 'Event Storming', link: '/03-design/03-01-event-storming', },
              { text: 'Domain', link: '/03-design/03-02-domain', },
              { text: 'Architecture', link: '/03-design/03-03-architecture', },
              { text: 'Microservices', link: '/03-design/03-04-microservices', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'Implementation', link: "/04-implementation/04-00-index", collapsed: false,
            items: [
              { text: 'Microservices', link: '/04-implementation/04-01-implementation', },
              { text: 'Testing', link: '/04-implementation/04-02-testing', },
              { text: 'Multiplatform', link: '/04-implementation/04-03-multiplatform', },
              { text: 'Experiments', link: '/04-implementation/04-04-experiments', },
              { text: 'Monitoring', link: '/04-implementation/04-05-monitoring', },
            ]
          },
        ]
      },
      {
        items: [
          {
            text: 'DevOps', link: "/05-devops/05-00-devops", collapsed: false,
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
