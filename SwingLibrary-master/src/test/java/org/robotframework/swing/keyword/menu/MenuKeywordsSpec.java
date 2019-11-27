package org.robotframework.swing.keyword.menu;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.menu.MenuSupport;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class MenuKeywordsSpec extends MockSupportSpecification<MenuKeywords> {
    private String menuPath = "some|path";
    private JMenuItemOperator menuItemOperator;
    private JMenuBarOperator menuBarOperator;

    public class Any {
        public MenuSupport create() {
            return new MenuKeywords();
        }

        public void isRobotKeywordAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectFromMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMenu")));
        }

        public void hasSelectFromMenuBlockKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMenuAndWait")));
        }

        public void hasMenuItemShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldBeEnabled")));
        }

        public void hasMenuItemShouldNotBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldNotBeEnabled")));
        }

        public void hasMenuItemShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldExist")));
        }

        public void hasMenuItemShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldNotExist")));
        }
    }

    public class OperatingOnMenus {
        public MenuKeywords create() {
            return createMenuKeywordsWithMockInternals();
        }

        public void selectsFromMenu() {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
                one(menuItemOperator).pushNoBlock();
            }});

            context.selectFromMenu(menuPath);
        }

        public void selectsFromDisabledMenuAndFails() {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
                never(menuItemOperator).pushNoBlock();
            }});
            specify(new Block() {
                public void run() throws Throwable {
                    context.selectFromMenu(menuPath);
                }
            }, should.raise(AssertionError.class));
        }

        public void selectsFromMenuAndWaits() {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
                one(menuItemOperator).push();
            }});

            context.selectFromMenuAndWait(menuPath);
        }
    }

    public class CheckingMenuItemExistence {
        private IComponentConditionResolver menuExistenceResolver;

        public MenuKeywords create() {
            menuExistenceResolver = mock(IComponentConditionResolver.class);
            menuBarOperator = mock(JMenuBarOperator.class);

            checking(new Expectations() {{
                one(menuBarOperator).pressMouse();
            }});

            return new MenuKeywords() {
                protected JMenuBarOperator menubarOperator() {
                    return menuBarOperator;
                }

                IComponentConditionResolver createMenuItemExistenceResolver() {
                    return menuExistenceResolver;
                }
            };
        }

        public void menuItemShouldExistPassesIfMenuItemExists() throws Throwable {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldExist(menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void menuItemShouldExistFailsIfMenuItemDoesntExists() {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldExist(menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' does not exist."));
        }

        public void menuItemShouldNotExistPassesIfMenuItemDoesntExist() throws Throwable {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotExist(menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void menuItemShouldNotExistFailsIfMenuItemExists() {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotExist(menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' exists."));
        }
    }

    public class CheckingMenuItemState {
        public MenuSupport create() {
            MenuSupport menuKeywords = createMenuKeywordsWithMockInternals();
            expectMenuToClose();
            return menuKeywords;
        }
        public void menuItemShouldBeEnabledPassesIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeEnabled(menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void menuItemShouldBeEnabledFailsIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeEnabled(menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' is disabled."));
        }

        public void menuItemShouldNotBeEnabledPassesIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeEnabled(menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void menuItemShouldNotBeEnabledFailsIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeEnabled(menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' is enabled."));
        }

        private void expectMenuToClose() {
            checking(new Expectations() {{
                one(menuBarOperator).pressMouse();
            }});
        }
    }

    public class HandlingAliases {
        protected boolean menuItemShouldNotBeEnabledWasCalled = false;

        public MenuSupport create() {
            return new MenuKeywords() {
                public void menuItemShouldNotBeEnabled(String menuPath) {
                    menuItemShouldNotBeEnabledWasCalled  = true;
                }
            };
        }

        public void menuItemShouldBeDisabledIsAnAliasForMenuItemShouldNotBeEnabled() {
            context.menuItemShouldBeDisabled(menuPath);
            specify(menuItemShouldNotBeEnabledWasCalled);
        }
    }

    private MenuKeywords createMenuKeywordsWithMockInternals() {
        menuBarOperator = mock(JMenuBarOperator.class);
        MenuKeywords menuKeywords = new MenuKeywords() {
            protected JMenuBarOperator menubarOperator() {
                return menuBarOperator;
            }
        };
        menuItemOperator = mock(JMenuItemOperator.class);

        checking(new Expectations() {{
            atLeast(1).of(menuBarOperator).showMenuItem(menuPath); will(returnValue(menuItemOperator));

            atLeast(1).of(menuItemOperator).setComparator(with(any(EqualsStringComparator.class)));
            atLeast(1).of(menuItemOperator).grabFocus();
        }});
        return menuKeywords;
    }

}
