import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';

//主题
//1.github主题
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
//2.vuepress主题
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

// 代码高亮
import hljs from 'highlight.js';
import Prism from 'prismjs';

//复制代码
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
//代码行号
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
//高亮代码行
import createHighlightLinesPlugin from '@kangc/v-md-editor/lib/plugins/highlight-lines/index';
import '@kangc/v-md-editor/lib/plugins/highlight-lines/highlight-lines.css';


export function addMdEditor(app) {
    // VMdEditor.use(githubTheme, {
    //     Hljs: hljs,
    // });

    VMdEditor.use(vuepressTheme, {
        Prism,
    });

    VMdEditor.use(createCopyCodePlugin());
    VMdEditor.use(createLineNumbertPlugin());
    VMdEditor.use(createHighlightLinesPlugin());

    return VMdEditor
}